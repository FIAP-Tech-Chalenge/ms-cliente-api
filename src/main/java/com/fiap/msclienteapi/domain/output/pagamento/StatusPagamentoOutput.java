package com.fiap.msclienteapi.domain.output.pagamento;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
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
    private final StatusPagamento statusPagamento;
    private OutputStatus outputStatus;

    public StatusPagamentoOutput(StatusPagamento statusPagamento, OutputStatus outputStatus) {
        this.outputStatus = outputStatus;
        this.statusPagamento = statusPagamento;
    }

    public Object getBody() {
        return this.statusPagamento;
    }
}
