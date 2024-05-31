package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.pedido.BuscaPedidoOutput;
import com.fiap.msclienteapi.domain.outputerror.BuscarPedidoOutputError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class BuscaPedidoPorUuidUseCase {

    private final BuscaPedidoInterface buscaPedido;
    private OutputInterface buscaPedidoOutput;

    public void execute(UUID pedidoUuid, UUID clienteUuid) {
        try {
            Pedido pedidoEntity = this.buscaPedido.encontraPedidoPorUuid(pedidoUuid, clienteUuid);

            if (pedidoEntity == null) {
                this.buscaPedidoOutput = new BuscarPedidoOutputError(
                        new OutputStatus(404, "Not found", "Pedido não encontrado")
                );
                return;
            }

            this.buscaPedidoOutput = new BuscaPedidoOutput(
                    pedidoEntity,
                    new OutputStatus(200, "OK", "Pedido encontrado")
            );

        } catch (PedidoNaoEncontradoException e) {
            this.buscaPedidoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(404, "Not Found", "Produto não encontrado")
            );
        } catch (Exception e) {
            this.buscaPedidoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}
