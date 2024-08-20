package com.fiap.msclienteapi.application.controllers.cliente.identifica.requests;

public record InativacaoClienteRequest(String nome, String endereco, String numeroTelefone, boolean dadosDePagamento) {
}
