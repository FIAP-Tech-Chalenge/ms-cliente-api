package com.fiap.msclienteapi.infra.stream.producers;

import com.fiap.msclienteapi.domain.gateway.producers.PagamentoProducerInterface;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;

public class PagamentoAprovadoProducer implements PagamentoProducerInterface {
    @Override
    public void send(StatusPagamentoOutput message) {

    }
}
