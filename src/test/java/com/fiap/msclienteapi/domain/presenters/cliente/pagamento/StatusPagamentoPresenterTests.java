package com.fiap.msclienteapi.domain.presenters.cliente.pagamento;

import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class StatusPagamentoPresenterTests {
        
    @Mock
    StatusPagamentoOutput statusPagamentoOutput;
    
    StatusPagamentoPresenter statusPagamentoPresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        statusPagamentoPresenter = new StatusPagamentoPresenter(statusPagamentoOutput);
    }

    @Test
    public void deveObterUmOutput() {
        when(statusPagamentoOutput.getStatusPagamento()).thenReturn(StatusPagamento.PAGO);

        var checkoutRetornado = statusPagamentoPresenter.getOutput().getStatusPagamento();
        assertThat(checkoutRetornado).isEqualTo(StatusPagamento.PAGO);
    }

    @Test
    void deveRetornarUmArrayDeCliente() {
        
        var statusPagamentoArray = new HashMap<>();
        statusPagamentoArray.put("status_pagamento", StatusPagamento.PAGO);

        when(statusPagamentoOutput.getStatusPagamento()).thenReturn(StatusPagamento.PAGO);

        var statusRetornado = statusPagamentoPresenter.toArray();
        assertThat(statusRetornado).isEqualTo(statusPagamentoArray);
    }
}