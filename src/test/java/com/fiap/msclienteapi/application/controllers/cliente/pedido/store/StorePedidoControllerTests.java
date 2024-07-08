package com.fiap.msclienteapi.application.controllers.cliente.pedido.store;

import com.fiap.msclienteapi.application.controllers.cliente.pedido.store.requests.StorePedidoRequest;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.gateway.pedido.PedidoInterface;
import com.fiap.msclienteapi.domain.input.pedido.ProdutoPedidoInput;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.model.ProdutoModel;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StorePedidoControllerTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @Mock 
    HttpServletRequest request;

    @Mock
    PedidoInterface pedidoInterface;

    @InjectMocks
    StorePedidoController storePedidoController;

    @Test
    public void deveCriarPedidoComSucesso(){
        UUID cliendeUUID = UUID.randomUUID();
        UUID produtoUUID = UUID.randomUUID();
        ClienteModel cliente = new ClienteModel("Cliente", "111", "cliente@cliente.com", cliendeUUID);
        ArrayList<ProdutoPedidoInput> produtos = new ArrayList<ProdutoPedidoInput>();
        produtos.add(new ProdutoPedidoInput(produtoUUID, 1, CategoriaEnum.BEBIDA));
        ProdutoModel produto = new ProdutoModel(produtoUUID, "coca", 10.0f, "refrigerante", CategoriaEnum.BEBIDA, 1);
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setClienteId(cliendeUUID);
        pedidoModel.setUuid(produtoUUID);
        StorePedidoRequest storePedidoRequest = new StorePedidoRequest(cliendeUUID, produtos, 10l);

        when(clienteRepository.findByUuid(cliendeUUID)).thenReturn(cliente);
        when(produtoRepository.findByUuid(produtoUUID)).thenReturn(produto);
        when(pedidoRepository.save(any(PedidoModel.class))).thenReturn(pedidoModel);
        
        try {
            ResponseEntity<Object> response = storePedidoController.criaPedido(storePedidoRequest, request);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        } catch (Exception e) {}
    }
}