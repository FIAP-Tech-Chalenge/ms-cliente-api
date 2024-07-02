package com.fiap.msclienteapi.domain.generic.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

public class OutputErrorTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        OutputStatus outputStatus = new OutputStatus(200, "sucesso", "mensagem");
        OutputError outputError = new OutputError("mensagem de erro", outputStatus);
        assertThat(outputError.getMensagem()).isEqualTo("mensagem de erro");
        assertThat(outputError.getOutputStatus().getCode()).isEqualTo(200);
        assertThat(outputError.getOutputStatus().getCodeName()).isEqualTo("sucesso");
        assertThat(outputError.getOutputStatus().getMessage()).isEqualTo("mensagem");
    }

    @Test
    public void deveRetornarOCorpoDaMensagem() {
        OutputStatus outputStatus = new OutputStatus(200, "sucesso", "mensagem");
        OutputError outputError = new OutputError("mensagem de erro", outputStatus);
        assertInstanceOf(OutputStatus.class, outputError.getBody());
        assertThat(outputError.getOutputStatus().getCode()).isEqualTo(200);
        assertThat(outputError.getOutputStatus().getCodeName()).isEqualTo("sucesso");
        assertThat(outputError.getOutputStatus().getMessage()).isEqualTo("mensagem");
    }
}
