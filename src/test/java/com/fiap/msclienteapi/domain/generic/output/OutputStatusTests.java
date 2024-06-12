package com.fiap.msclienteapi.domain.generic.output;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OutputStatusTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        OutputStatus outputStatus = new OutputStatus(200, "sucesso", "mensagem");
        assertThat(outputStatus.getCode()).isEqualTo(200);
        assertThat(outputStatus.getCodeName()).isEqualTo("sucesso");
        assertThat(outputStatus.getMessage()).isEqualTo("mensagem");
    }
}
