package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.pedido.BuscaTodosPedidoOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AcompanhaPedidosUseCase {
    
    private final BuscaPedidoInterface buscaPedidoInterface;
    private OutputInterface buscaProdutoOutput;

    public void execute() {
        try {
            List<Pedido> listPedidos = buscaPedidoInterface.retornaTodosPorStatusPedido(StatusPedido.EM_PREPARACAO);

            Collections.sort(listPedidos, Comparator.comparing(Pedido::getNumeroPedido));

            int tempoCumulativo = 0;

            for (Pedido pedido : listPedidos) {
                tempoCumulativo += pedido.getTempoDePreparoEmMinutos();
                pedido.setTempoDePreparoEmMinutos(tempoCumulativo);
            }
            
            buscaProdutoOutput = new BuscaTodosPedidoOutput(
                    listPedidos,
                    new OutputStatus(200, "OK", "Lista de pedidos")
            );

        } catch (Exception e) {
            buscaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}
