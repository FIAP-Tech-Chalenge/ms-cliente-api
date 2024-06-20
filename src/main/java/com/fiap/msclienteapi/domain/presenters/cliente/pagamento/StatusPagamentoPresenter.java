package com.fiap.msclienteapi.domain.presenters.cliente.pagamento;

import com.fiap.msclienteapi.domain.generic.presenter.PresenterInterface;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;

import java.util.HashMap;
import java.util.Map;

public class StatusPagamentoPresenter implements PresenterInterface {
    private final StatusPagamentoOutput statusPagamentoOutput;

    public StatusPagamentoPresenter(StatusPagamentoOutput statusPagamentoOutput) {
        this.statusPagamentoOutput = statusPagamentoOutput;
    }

    public Map<String, Object> toArray() {
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("status_pagamento", this.statusPagamentoOutput.getPedidoEntity().getStatusPagamento());
        return cliente;
    }

    public StatusPagamentoOutput getOutput() {
        return this.statusPagamentoOutput;
    }
}
