package com.fiap.msclienteapi.domain.presenters.cliente.pagamento;

import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.output.pedido.CheckOutOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProcessarPagamentoPresenterTests {
    
    @Mock
    CheckOutOutput checkOutOutput;
    
    ProcessarPagamentoPresenter processarPagamentoPresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        processarPagamentoPresenter = new ProcessarPagamentoPresenter(checkOutOutput);
    }

    @Test
    public void deveObterUmOutput() {
        UUID checkoutUUID = UUID.randomUUID();
        Checkout checkout = new Checkout(checkoutUUID, StatusPagamento.PAGO);
        when(checkOutOutput.getCheckout()).thenReturn(checkout);

        var checkoutRetornado = processarPagamentoPresenter.getOutput().getCheckout();
        assertThat(checkoutRetornado.getUuidPedido()).isEqualTo(checkoutUUID);
        assertThat(checkoutRetornado.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
    }

    @Test
    void deveRetornarUmArrayDeCliente() {
        UUID checkoutUUID = UUID.randomUUID();
        Checkout checkout = new Checkout(checkoutUUID, StatusPagamento.PAGO);
        
        var clienteArray = new HashMap<>();
        clienteArray.put("status_pagamento", checkout.getStatusPagamento());
        clienteArray.put("qr_code", checkout.getQrCode());
        clienteArray.put("uuid_pedido", checkout.getUuidPedido());

        when(checkOutOutput.getCheckout()).thenReturn(checkout);

        var clienteRetornado = processarPagamentoPresenter.toArray();
        assertThat(clienteRetornado).isEqualTo(clienteArray);
    }
}
