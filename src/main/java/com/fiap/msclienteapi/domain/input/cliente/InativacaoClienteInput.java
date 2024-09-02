package com.fiap.msclienteapi.domain.input.cliente;

import lombok.Getter;

@Getter
public class InativacaoClienteInput {

    private String nome;
    private String endereco;
    private String numeroTelefone;
    private boolean dadosDePagamento;

    public InativacaoClienteInput(String nome, String endereco, String numeroTelefone, boolean dadosDePagamento) {
        this.nome = nome;
        this.endereco = endereco;
        this.numeroTelefone = numeroTelefone;
        this.dadosDePagamento = dadosDePagamento;
    }
}
