package com.fiap.msclienteapi.domain.presenters.cliente.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Cliente;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.entity.pedido.Produto;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.output.pedido.BuscaPedidoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetPedidoPresenterTests {
    
    @Mock
    BuscaPedidoOutput buscaPedidoOutput;
    
    GetPedidoPresenter getPedidoPresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        getPedidoPresenter = new GetPedidoPresenter(buscaPedidoOutput);
    }

    @Test
    public void deveObterUmOutput() {
        UUID pedidoUuid = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUuid);

        when(buscaPedidoOutput.getPedido()).thenReturn(pedido);

        var pedidoRetornado = getPedidoPresenter.getOutput().getPedido();
        assertThat(pedidoRetornado.getClienteUuid()).isEqualTo(pedidoUuid);
    }

    @Test
    void deveRetornarUmArray() {
        Cliente cliente = new Cliente("cliente", "000", "cliente@cliente.com", UUID.randomUUID());
        
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(new Produto(UUID.randomUUID(), 1, CategoriaEnum.ACOMPANHAMENTO));

        Pedido pedido = new Pedido(UUID.randomUUID(), cliente.getUuid(), StatusPedido.PRONTO, StatusPagamento.PAGO, 20, 10.0f);
        pedido.setProdutos(produtos);
        
        Map<String, Object> pedidoArray = new HashMap<>();
        pedidoArray.put("uuid_pedido", pedido.getUuid());
        pedidoArray.put("status_pagamento", pedido.getStatusPagamento());
        pedidoArray.put("numero_pedido", pedido.getNumeroPedido());
        pedidoArray.put("total", pedido.getTotal());
        pedidoArray.put("cliente_uuid", pedido.getClienteUuid());
        pedidoArray.put("status_pedido", pedido.getStatusPedido());

        List<Map<String, Object>> produtosMapList = new ArrayList<>();
        for (Produto produto : pedido.getProdutos()) {
            Map<String, Object> produtoMap = new HashMap<>();
            produtoMap.put("uuid", produto.getUuid().toString());
            produtoMap.put("quantidade", produto.getQuantidade());
            produtoMap.put("valor", produto.getValor());
            produtoMap.put("categoria", produto.getCategoria().toString());
            produtosMapList.add(produtoMap);
        }
        pedidoArray.put("produtos", produtosMapList);
        

        when(buscaPedidoOutput.getPedido()).thenReturn(pedido);

        var pedidoRetornado = getPedidoPresenter.toArray();
        assertThat(pedidoRetornado).isEqualTo(pedidoArray);
    }
}