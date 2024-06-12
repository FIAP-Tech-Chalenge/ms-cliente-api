package com.fiap.msclienteapi.domain.output.pagamento;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;


public class StatusPagamentoOutputTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        StatusPagamentoOutput statusPagamentoOutput = new StatusPagamentoOutput(StatusPagamento.PAGO, outputStatus);

        assertThat(statusPagamentoOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(statusPagamentoOutput.getBody()).isInstanceOf(StatusPagamento.class);
    }
}
