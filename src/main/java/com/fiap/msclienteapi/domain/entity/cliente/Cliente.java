package com.fiap.msclienteapi.domain.entity.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.validation.IdentificaClienteValidation;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    private UUID uuid;
    private String nome;
    private String cpf;
    private String email;

    public Cliente(String nome, String cpf, String email, UUID uuid) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.uuid = uuid;
    }
    public Cliente identificarCliente() throws Exception {
        Cliente cliente = new IdentificaClienteValidation().validaEntidade(this);
        this.setUuid(getUuid());
        return cliente;
    }
}
