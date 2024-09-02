package com.fiap.msclienteapi.domain.gateway.producers;

import com.fiap.msclienteapi.domain.output.cliente.InativacaoClienteOutput;

public interface InativarClienteProducerInterface {
    void send(InativacaoClienteOutput clienteOutput);
}
