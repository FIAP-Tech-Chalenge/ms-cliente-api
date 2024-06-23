package com.fiap.msclienteapi.domain.gateway.producers;


import com.fiap.msclienteapi.domain.generic.output.PedidoOutput;

public interface PedidoProducerInterface {
    void send(PedidoOutput pedidoOutput);
}
