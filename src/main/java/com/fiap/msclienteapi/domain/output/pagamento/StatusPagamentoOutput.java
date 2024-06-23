package com.fiap.msclienteapi.domain.output.pagamento;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class StatusPagamentoOutput implements OutputInterface {
    private final Pedido pedidoEntity;
    private OutputStatus outputStatus;

    public StatusPagamentoOutput(Pedido pedidoEntity, OutputStatus outputStatus) {
        this.outputStatus = outputStatus;
        this.pedidoEntity = pedidoEntity;
    }

    public Object getBody() {
        return this.pedidoEntity;
    }
}
