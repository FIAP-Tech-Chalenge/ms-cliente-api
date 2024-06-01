package com.fiap.msclienteapi.domain.gateway.producers;

import com.fiap.msclienteapi.domain.generic.output.OutputInterface;

public interface NovoClienteProducertInterface {
    void send(OutputInterface message);
}
