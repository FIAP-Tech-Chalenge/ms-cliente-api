package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.domain.gateway.pedido.PedidoInterface;
import com.fiap.msclienteapi.domain.gateway.producers.PedidoEntregueProducerInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.pedido.CriaPedidoOutput;
import com.fiap.msclienteapi.domain.outputerror.BuscarPedidoOutputError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ConfrimaEntregaPedidoUseCase {

    private final PedidoInterface pedidoInterface;
    private final BuscaPedidoInterface buscaPedidoInterface;
    private final PedidoEntregueProducerInterface pedidoEntregueProducerInterface;
    private OutputInterface outputInterface;

    @Transactional
    public void execute(UUID uuidPedido, UUID uuidClientResolved) {
        try {
            Pedido pedidoEntity = buscaPedidoInterface.encontraPedidoShortPorUuid(uuidPedido, uuidClientResolved);
            if (pedidoEntity == null) {
                this.outputInterface = new BuscarPedidoOutputError(
                        new OutputStatus(404, "Not found", "Pedido não encontrado")
                );
                return;
            }
            this.outputInterface = new CriaPedidoOutput(
                    pedidoEntity,
                    new OutputStatus(200, "Ok", "Pedido entregue")
            );

        } catch (Exception e) {
            this.outputInterface = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Error", e.getMessage())
            );
        }  finally {
            if (this.outputInterface instanceof CriaPedidoOutput) {
                this.pedidoEntregueProducerInterface.send((CriaPedidoOutput) this.outputInterface);
            }
        }
    }
}
