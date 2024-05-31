package com.fiap.msclienteapi.domain.gateway.cliente;

import com.fiap.msclienteapi.domain.entity.pedido.Cliente;
import com.fiap.msclienteapi.domain.exception.pedido.ClienteNaoEncontradoException;

import java.util.UUID;


public interface ClienteInterface {
    Cliente buscaClientePorUuid(UUID uuid) throws ClienteNaoEncontradoException;
}
