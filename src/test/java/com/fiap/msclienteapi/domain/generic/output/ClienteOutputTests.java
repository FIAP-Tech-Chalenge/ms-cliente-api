package com.fiap.msclienteapi.domain.generic.output;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClienteOutputTests {
    
    @Test
    public void deveCriarUmClienteOutput() {
        ClienteOutput clienteOutput = new ClienteOutput(UUID.randomUUID(), "cliente", "0000", "cliente@cliente.com");
        Object body = clienteOutput.getBody();
        assertThat(((ClienteOutput) body).getNome()).isEqualTo("cliente");
        assertThat(((ClienteOutput) body).getCpf()).isEqualTo("0000");
        assertThat(((ClienteOutput) body).getEmail()).isEqualTo("cliente@cliente.com");
    }
    
    @Test
    public void deveRetornarUmOutputStatus() {
        ClienteOutput clienteOutput = new ClienteOutput(UUID.randomUUID(), "cliente", "0000", "cliente@cliente.com");
        OutputStatus outputStatus = clienteOutput.getOutputStatus();
        assertThat(outputStatus.getCode()).isEqualTo(200);
        assertThat(outputStatus.getCodeName()).isEqualTo("Ok");
        assertThat(outputStatus.getMessage()).isEqualTo("Cliente encontrado");
    }
}
