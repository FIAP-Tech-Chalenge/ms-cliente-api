package com.fiap.msclienteapi.domain.useCase.pagamento;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.domain.gateway.pedido.PedidoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class MudaStatusPagamentoUseCase {
    private final BuscaPedidoInterface buscaPedido;
    private final PedidoInterface pedidoInterface;
    private OutputInterface buscaPedidoOutput;

    public void execute(UUID uuid, StatusPagamento statusPagamento) {
        try {
            Pedido pedidoEntity = this.buscaPedido.encontraPedidoPorUuid(uuid, null);
            pedidoEntity = this.pedidoInterface.atualizaPagamento(pedidoEntity, statusPagamento);

            this.buscaPedidoOutput = new StatusPagamentoOutput(
                    pedidoEntity.getStatusPagamento(),
                    new OutputStatus(200, "OK", "Status do pagamento atualizado")
            );
        } catch (PedidoNaoEncontradoException e) {
            this.buscaPedidoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(404, "Not Found", "Pedido não encontrado")
            );
        } catch (Exception e) {
            this.buscaPedidoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}
