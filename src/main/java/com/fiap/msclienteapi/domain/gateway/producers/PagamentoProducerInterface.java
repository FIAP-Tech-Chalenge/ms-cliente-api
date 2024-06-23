package com.fiap.msclienteapi.domain.gateway.producers;

import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;

public interface PagamentoProducerInterface {
    void send(StatusPagamentoOutput statusPagamentoOutput);
}
