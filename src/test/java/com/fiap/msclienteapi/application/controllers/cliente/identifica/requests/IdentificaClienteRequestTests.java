package com.fiap.msclienteapi.application.controllers.cliente.identifica.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentificaClienteRequestTests {

    @Test
	public void deveSerInstanciadaCorretamente() {
        String nome = "Aloha";
        String cpf = "000000000000";
        String email = "aloha@aloha.com";

        IdentificaClienteRequest identificaClienteRequest = new IdentificaClienteRequest(nome, cpf, email);

        assertEquals("Aloha", identificaClienteRequest.nome());
        assertEquals("000000000000", identificaClienteRequest.cpf());
        assertEquals("aloha@aloha.com", identificaClienteRequest.email());
    }
}
