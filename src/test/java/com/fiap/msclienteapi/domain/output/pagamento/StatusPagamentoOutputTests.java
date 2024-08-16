package com.fiap.msclienteapi.domain.output.pagamento;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class StatusPagamentoOutputTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        StatusPagamentoOutput statusPagamentoOutput = new StatusPagamentoOutput(new Pedido(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454")), outputStatus);

        assertThat(statusPagamentoOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(statusPagamentoOutput.getBody()).isInstanceOf(Pedido.class);
    }
}
