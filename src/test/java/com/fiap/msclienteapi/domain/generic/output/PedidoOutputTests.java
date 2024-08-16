package com.fiap.msclienteapi.domain.generic.output;

import com.fiap.msclienteapi.domain.entity.pedido.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PedidoOutputTests {
    
    @Test
    public void deveCriarUmPedidoOutput() {
        PedidoOutput pedidoOutput = new PedidoOutput(10.0d, UUID.randomUUID(), "PAGO", new ArrayList<Produto>(), 1, "Pago", UUID.randomUUID());
        assertThat(pedidoOutput.getTotal()).isEqualTo(10.0d);
        assertThat(pedidoOutput.getStatus_pagamento()).isEqualTo("Pago");
        assertThat(pedidoOutput.getStatus_pedido()).isEqualTo("PAGO");
    }

    @Test
    public void deveObterCorpoDoPedido() {
        PedidoOutput pedidoOutput = new PedidoOutput(10.0d, UUID.randomUUID(), "PAGO", new ArrayList<Produto>(), 1, "Pago", UUID.randomUUID());
        Object body = pedidoOutput.getBody();
        assertThat(((PedidoOutput) body).getTotal()).isEqualTo(10.0d);
        assertThat(((PedidoOutput) body).getStatus_pagamento()).isEqualTo("Pago");
        assertThat(((PedidoOutput) body).getStatus_pedido()).isEqualTo("PAGO");
        
    }

    @Test
    public void deveRetornarMensagemDeStatusDoPedido() {
        PedidoOutput pedidoOutput = new PedidoOutput(10.0d, UUID.randomUUID(), "PAGO", new ArrayList<Produto>(), 1, "Pago", UUID.randomUUID());
        OutputStatus outputStatus = pedidoOutput.getOutputStatus();
        assertThat(outputStatus.getCode()).isEqualTo(200);
        assertThat(outputStatus.getCodeName()).isEqualTo("Ok");
        assertThat(outputStatus.getMessage()).isEqualTo("Pedido encontrado");
    }

}
