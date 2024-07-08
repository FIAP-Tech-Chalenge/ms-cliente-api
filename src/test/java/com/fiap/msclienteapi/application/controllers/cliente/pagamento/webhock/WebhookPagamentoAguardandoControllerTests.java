package com.fiap.msclienteapi.application.controllers.cliente.pagamento.webhock;

import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WebhookPagamentoAguardandoControllerTests {
    
    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @Mock
    ProdutoRepository produtoRepository;

    @InjectMocks
    WebhookPagamentoAguardandoController webhookPagamentoAguardandoController;

    @Test
    public void deveRetornarSucesso() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        PedidoModel pedido = new PedidoModel();
        pedido.setUuid(pedidoUUID);
        pedido.setClienteId(clienteUUID);
        when(pedidoRepository.findByUuid(pedidoUUID)).thenReturn(pedido);

        ResponseEntity<Object> response = webhookPagamentoAguardandoController.pagamentoAguardandoPagamento(pedidoUUID);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }
}