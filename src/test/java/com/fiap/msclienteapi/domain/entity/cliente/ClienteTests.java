package com.fiap.msclienteapi.domain.entity.cliente;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class ClienteTests {

    @Test
    public void devePermitirIncializarClienteSemArgumentos() {
        var uuid = UUID.randomUUID();
        var nome = "Cliente 1";
        var email = "cliente1@email.com";
        var cpf = "12345678912";

        Cliente cliente = new Cliente();
        cliente.setUuid(uuid);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setCpf(cpf);

        assertThat(cliente.getUuid()).isEqualTo(uuid);
        assertThat(cliente.getNome()).isEqualTo(nome);
        assertThat(cliente.getEmail()).isEqualTo(email);
        assertThat(cliente.getCpf()).isEqualTo(cpf);
    }

    @Test
    public void deveCriarUmaInstanciaDeCliente() {
        Cliente cliente = new Cliente("cliente", "1234567890", "email@email.com", UUID.randomUUID());
        assertInstanceOf(Cliente.class, cliente);
    }
}
