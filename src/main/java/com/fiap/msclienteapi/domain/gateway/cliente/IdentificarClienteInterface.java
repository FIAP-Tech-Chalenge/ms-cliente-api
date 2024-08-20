package com.fiap.msclienteapi.domain.gateway.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;


public interface IdentificarClienteInterface {

    Cliente buscaClientePorNome(String nome);
    Cliente inativarCliente(Cliente cliente);
    Cliente buscaClientePorCpf(String cpf);
    Cliente identificarCliente(Cliente cliente);
}
