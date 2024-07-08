package com.fiap.msclienteapi.application.controllers.cliente.pedido.get;

import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import jakarta.servlet.http.HttpServletRequest;
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
public class AcompanharPedidoControllerTests {
    
    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @Mock 
    HttpServletRequest request;

    @InjectMocks
    AcompanharPedidoController acompanharPedidoController;

    @Test
    public void deveRetornarUmSucessoAoTentarBuscarPedido(){
        UUID pedidoUUId = UUID.randomUUID();
        UUID cliente = UUID.randomUUID();

        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setUuid(pedidoUUId);
        pedidoModel.setClienteId(cliente);
        when(pedidoRepository.findByUuid(pedidoUUId)).thenReturn((pedidoModel));

        try {
            ResponseEntity<Object> response = acompanharPedidoController.getPedido(pedidoUUId, cliente, request);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarErroAoNaoEncontrarRelacionamentoComCliente(){
        UUID pedidoUUId = UUID.randomUUID();
        UUID cliente = UUID.randomUUID();

        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setUuid(pedidoUUId);
        pedidoModel.setClienteId(cliente);
        when(pedidoRepository.findByUuid(pedidoUUId)).thenReturn((pedidoModel));
        
        try {
            ResponseEntity<Object> response = acompanharPedidoController.getPedido(pedidoUUId, UUID.randomUUID(), request);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        } catch (Exception e) {}
    }
}