package com.fiap.msclienteapi.domain.gateway.producers;

import com.fiap.msclienteapi.domain.generic.output.OutputInterface;

public interface PagamentoProducerInterface {
    void send(OutputInterface message);
}
