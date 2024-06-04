package com.fiap.msclienteapi.domain.gateway.producers;

import com.fiap.msclienteapi.domain.generic.output.ClienteOutput;

public interface NovoClienteProducertInterface {
    void send(ClienteOutput clienteOutput);
}
