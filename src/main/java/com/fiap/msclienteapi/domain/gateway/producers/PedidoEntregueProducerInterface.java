package com.fiap.msclienteapi.domain.gateway.producers;

import com.fiap.msclienteapi.domain.output.pedido.CriaPedidoOutput;

public interface PedidoEntregueProducerInterface {
    void send(CriaPedidoOutput statusPagamentoOutput);
}
