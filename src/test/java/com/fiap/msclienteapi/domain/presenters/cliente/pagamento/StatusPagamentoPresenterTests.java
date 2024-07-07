package com.fiap.msclienteapi.domain.presenters.cliente.pagamento;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class StatusPagamentoPresenterTests {
        
    @Mock
    StatusPagamentoOutput statusPagamentoOutput;
    
    @InjectMocks
    StatusPagamentoPresenter statusPagamentoPresenter;

    @Test
    public void deveIntanciarPresenterCorretamente() {
        UUID pedidoUuid = UUID.randomUUID();
        OutputStatus outputStatus = new OutputStatus(200, "Ok", "ok");
        StatusPagamentoOutput statusPagamentoOutput = new StatusPagamentoOutput(new Pedido(pedidoUuid), outputStatus);
        StatusPagamentoPresenter statusPagamentoPresenter = new StatusPagamentoPresenter(statusPagamentoOutput);
        StatusPagamentoOutput output = statusPagamentoPresenter.getOutput();
        assertThat(output.getPedidoEntity().getClienteUuid()).isEqualTo(pedidoUuid);
    }   

    @Test
    public void deveObterUmArray() {
        OutputStatus outputStatus = new OutputStatus(200, "Ok", "ok");
        Pedido pedido = new Pedido(UUID.randomUUID(), UUID.randomUUID(), StatusPedido.PRONTO, StatusPagamento.PAGO, 10.0f);
        StatusPagamentoOutput statusPagamentoOutput = new StatusPagamentoOutput(pedido, outputStatus);
        StatusPagamentoPresenter statusPagamentoPresenter = new StatusPagamentoPresenter(statusPagamentoOutput);

        Map<String, Object> valorRetornado = statusPagamentoPresenter.toArray();
        
        Map<String, Object> valorEsperado = new HashMap<>();
        valorEsperado.put("status_pagamento", StatusPagamento.PAGO);

        assertThat(valorRetornado, is(valorEsperado));
    }

}