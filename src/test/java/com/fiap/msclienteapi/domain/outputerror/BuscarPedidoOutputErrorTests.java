package com.fiap.msclienteapi.domain.outputerror;

import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscarPedidoOutputErrorTests {
    @Test
    public void deveInstanciarClasseCorretamente() {
        OutputStatus outputStatus= new OutputStatus(0, "error", null);
        BuscarPedidoOutputError buscaProdutoOutput = new BuscarPedidoOutputError(outputStatus);

        assertThat(buscaProdutoOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(buscaProdutoOutput.getBody()).isNotNull();
    }
}
