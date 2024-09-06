package com.fiap.msclienteapi.infra.adpter.repository.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.entity.pedido.Produto;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.model.ProdutoModel;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriaPedidoRepositoryTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @InjectMocks
    CriaPedidoRepository criaPedidoRepository;

    @Test
    public void deveRetornarUmPedidoJaExistente(){
        UUID pedidoUUID = UUID.randomUUID();
        UUID produtoModelUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID, UUID.randomUUID(), StatusPedido.EM_PREPARACAO, StatusPagamento.PAGO,20,10.0f);
        pedido.setProdutos(new ArrayList<>());
        PedidoModel pedidoModel = new PedidoModel(pedido.getUuid(), 1l, pedido.getClienteUuid(), new Date(), pedido.getStatusPedido(), pedido.getStatusPagamento(), pedido.getTempoDePreparoEmMinutos(), pedido.getTotal());
        Produto produtoPedido =  new Produto(produtoModelUUID, 1, CategoriaEnum.ACOMPANHAMENTO);
        produtoPedido.setValor(10.0f);
        pedido.addProduto(produtoPedido);
        
        when(pedidoRepository.findMaxNumeroPedido()).thenReturn(1L);
        when(pedidoRepository.save(any(PedidoModel.class))).thenReturn(pedidoModel);
        
        try {
            Pedido pedidoRetornado = criaPedidoRepository.criaPedido(pedido);
            assertThat(pedidoRetornado.getNumeroPedido()).isEqualTo(pedido.getNumeroPedido());
            assertThat(pedidoRetornado.getTotal()).isEqualTo(pedido.getTotal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deveCriarUmPedidoCasoNaoExista(){
        UUID pedidoUUID = UUID.randomUUID();
        UUID produtoModelUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID, UUID.randomUUID(), StatusPedido.EM_PREPARACAO, StatusPagamento.PAGO, 20, 10.0f);
        pedido.setProdutos(new ArrayList<>());
        PedidoModel pedidoModel = new PedidoModel(pedido.getUuid(), 1l, pedido.getClienteUuid(), new Date(), pedido.getStatusPedido(), pedido.getStatusPagamento(), pedido.getTempoDePreparoEmMinutos(), pedido.getTotal());
        Produto produtoPedido =  new Produto(produtoModelUUID, 1, CategoriaEnum.ACOMPANHAMENTO);
        produtoPedido.setValor(10.0f);
        pedido.addProduto(produtoPedido);
        ProdutoModel produtoModel = new ProdutoModel(produtoModelUUID, "Cenoura", 10.0f, "Uma cenoura", CategoriaEnum.ACOMPANHAMENTO, 1);

        when(pedidoRepository.findMaxNumeroPedido()).thenReturn(1L);
        when(pedidoRepository.save(any(PedidoModel.class))).thenReturn(pedidoModel);
        when(produtoRepository.findByUuid(pedido.getProdutos().get(0).getUuid())).thenReturn(produtoModel);
        
        try {
            Pedido pedidoRetornado = criaPedidoRepository.criaPedido(pedido);
            assertThat(pedidoRetornado.getNumeroPedido()).isEqualTo(pedido.getNumeroPedido());
            assertThat(pedidoRetornado.getTotal()).isEqualTo(pedido.getTotal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deveGerarExcecao_ProdutoNaoTiverEstoqueSuficiente_ProdutoNaoEncontradoException(){
        UUID pedidoUUID = UUID.randomUUID();
        UUID produtoModelUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID, UUID.randomUUID(), StatusPedido.EM_PREPARACAO, StatusPagamento.PAGO, 20, 10.0f);
        pedido.setProdutos(new ArrayList<>());
        PedidoModel pedidoModel = new PedidoModel(pedido.getUuid(), 1l, pedido.getClienteUuid(), new Date(), pedido.getStatusPedido(), pedido.getStatusPagamento(), pedido.getTempoDePreparoEmMinutos(), pedido.getTotal());
        Produto produtoPedido =  new Produto(produtoModelUUID, 1, CategoriaEnum.ACOMPANHAMENTO);
        produtoPedido.setValor(10.0f);
        pedido.addProduto(produtoPedido);
        ProdutoModel produtoModel = new ProdutoModel(produtoModelUUID, "Cenoura", 10.0f, "Uma cenoura", CategoriaEnum.ACOMPANHAMENTO, 0);

        when(pedidoRepository.findMaxNumeroPedido()).thenReturn(1L);
        when(pedidoRepository.save(any(PedidoModel.class))).thenReturn(pedidoModel);
        when(produtoRepository.findByUuid(pedido.getProdutos().get(0).getUuid())).thenReturn(produtoModel);
        
        try {
            criaPedidoRepository.criaPedido(pedido);
        } catch (Exception e) {
            assertInstanceOf(ProdutoNaoEncontradoException.class, e);
            assertThat(e.getMessage()).isEqualTo("O produto está sem estoque");
        }
    }

    @Test
    public void deveAtualizarOStatusDoPagamento(){
        UUID pedidoUuid = UUID.randomUUID();
        PedidoModel pedidoModel = new PedidoModel(pedidoUuid, null, null, null, null, null ,0, null);
        Pedido pedido = new Pedido(pedidoUuid, UUID.randomUUID(), StatusPedido.RECEBIDO, StatusPagamento.NAO_PAGO, 20, 10.0f);
        
        when(pedidoRepository.findByUuid(pedido.getUuid())).thenReturn(pedidoModel);
        Pedido pedidoComStatusNovo = criaPedidoRepository.atualizaPagamento(pedido, StatusPagamento.PAGO);

        assertThat(pedidoComStatusNovo.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
    }

}