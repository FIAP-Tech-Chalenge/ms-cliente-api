package com.fiap.msclienteapi.domain.generic.output;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class ClienteOutput implements OutputInterface {
    private final UUID uuid;
    private final String nome;
    private final String cpf;
    private final String email;

    public ClienteOutput(UUID uuid, String nome, String cpf, String email) {
        this.uuid = uuid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    @Override
    public Object getBody() {
        return this;
    }

    @Override
    public OutputStatus getOutputStatus() {
        return new OutputStatus(200, "Ok", "Cliente encontrado");
    }



}
