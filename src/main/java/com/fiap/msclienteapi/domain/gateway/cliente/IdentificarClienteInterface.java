package com.fiap.msclienteapi.domain.gateway.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;


public interface IdentificarClienteInterface {

    Cliente buscaClientePorCpf(String cpf);
    Cliente identificarCliente(Cliente cliente);
}
