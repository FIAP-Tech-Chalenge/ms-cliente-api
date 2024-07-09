package com.fiap.msclienteapi.application.controllers.cliente.pedido.confirma;

import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.queue.kafka.producers.PedidoEntregueProducer;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfirmaPedidoControllerTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @Mock 
    HttpServletRequest request;
    
    @Mock
    PedidoEntregueProducer pedidoEntregueProducer;

    @InjectMocks
    ConfirmaPedidoController confirmaPedidoController;
    
    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(confirmaPedidoController, "servers", "localhost:9092");
    }
    
    @Test
    public void teste(){
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setClienteId(clienteUUID);
        pedidoModel.setUuid(pedidoUUID);
        when(pedidoRepository.findByUuid(pedidoUUID)).thenReturn(pedidoModel);

        try {
            ResponseEntity<Object> response = confirmaPedidoController.criaPedido(pedidoUUID, clienteUUID, request);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        } catch (Exception e) {}
    }
}
