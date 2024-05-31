package com.fiap.msclienteapi.domain.entity.cliente.validation;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.exception.cliente.EmailNaoPodeSerNuloException;
import com.fiap.msclienteapi.domain.exception.cliente.NomeNaoPodeSerNuloException;
import com.fiap.msclienteapi.domain.exception.valueObject.documento.CpfInvalidoValueObjectException;
import com.fiap.msclienteapi.domain.valuesObject.CpfValueObject;


public class IdentificaClienteValidation {

    public Cliente validaEntidade(Cliente cliente) throws Exception {
        if (cliente.getCpf().isEmpty()) {
            return cliente;
        }

        boolean CpfValue = new CpfValueObject(cliente.getCpf()).isValid();
        if (!CpfValue) {
            throw new CpfInvalidoValueObjectException("CPF inválido");
        }

        if (cliente.getNome().isEmpty()) {
            throw new NomeNaoPodeSerNuloException("Nome é obrigatório");
        }

        if (cliente.getEmail().isEmpty()) {
            throw new EmailNaoPodeSerNuloException("Email é obrigatório");
        }

        return new Cliente(cliente.getNome(), cliente.getCpf(), cliente.getEmail(), null);
    }
}
