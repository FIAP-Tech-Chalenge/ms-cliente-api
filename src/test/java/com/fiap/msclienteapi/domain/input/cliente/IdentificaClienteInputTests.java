package com.fiap.msclienteapi.domain.input.cliente;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class IdentificaClienteInputTests {

    @Test
    public void deveInstanciarClasseCorretamente() {
        IdentificaClienteInput identificaClienteInput = new IdentificaClienteInput("cliente", "123456789", "cliente@email.com");
        assertThat(identificaClienteInput.getNome()).isEqualTo("cliente");
        assertThat(identificaClienteInput.getCpf()).isEqualTo("123456789");
        assertThat(identificaClienteInput.getEmail()).isEqualTo("cliente@email.com");
    }
}
