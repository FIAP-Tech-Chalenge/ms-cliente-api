package com.fiap.msclienteapi.domain.output.cliente;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;

public class IdentificaClienteOutputTests {

    @Test
    public void deveInstanciarClasseCorretamente() {
        Cliente cliente = new Cliente("cliente", "0000", "email@email.com", UUID.randomUUID());
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        IdentificaClienteOutput identificaClienteOutput = new IdentificaClienteOutput(cliente, outputStatus);

        assertThat(identificaClienteOutput.getCliente().getNome()).isEqualTo("cliente");
        assertThat(identificaClienteOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(identificaClienteOutput.getBody()).isInstanceOf(Cliente.class);
    }
}
