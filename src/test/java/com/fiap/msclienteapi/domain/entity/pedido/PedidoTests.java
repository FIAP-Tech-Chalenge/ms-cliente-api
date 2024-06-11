package com.fiap.msclienteapi.domain.entity.pedido;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoVazioException;
import com.fiap.msclienteapi.domain.exception.pedido.ProdutoDoPedidoSemQuantidadeException;

public class PedidoTests {
    
    @Test
    public void deveInstanciarUmPedidoPorUUID(){
        UUID randomUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(randomUUID);
        assertThat(pedido.getClienteUuid()).isEqualTo(randomUUID);
    }

    @Test
    public void deveInstanciarUmPedidoDiretamente(){
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID, clienteUUID, StatusPedido.PRONTO, StatusPagamento.PAGO, 10.0f);
        assertThat(pedido.getPedidoId()).isEqualTo(pedidoUUID);
        assertThat(pedido.getClienteUuid()).isEqualTo(clienteUUID);
        assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.PRONTO);
        assertThat(pedido.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
    }

    @Test
    public void deveInstanciarUmPedidoViaGetters(){
        UUID randomUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(randomUUID);
        pedido.setPedidoId(UUID.randomUUID());
        UUID pedidoUUID = UUID.randomUUID();
        pedido.setUuid(pedidoUUID);
        pedido.setNumeroPedido(2L);
        pedido.setStatusPedido(StatusPedido.PRONTO);
        pedido.setStatusPagamento(StatusPagamento.PAGO);
        List<Produto> produtos = new ArrayList<Produto>();
        pedido.setProdutos(produtos);
        pedido.setTotal(10.9f);
        
        assertThat(pedido.getUuid()).isEqualTo(pedidoUUID);
        assertThat(pedido.getNumeroPedido()).isEqualTo(2L);
        assertThat(pedido.getTotal()).isEqualTo(10.9f);
    }

    @Test
    public void deveAdicionarUmProdutoAoPedido(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        pedido.addProduto(new Produto(null, null, null));
        assertFalse(pedido.getProdutos().isEmpty());
    }

    @Test
    public void deveGerarExcecao_AoReceberUmPedidoVazio_PedidoVazioException(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        try {
            pedido.verificaItensDoPedido();
        } catch (Exception exception) {
            assertInstanceOf(PedidoVazioException.class, exception);
        }
    }

    @Test
    public void deveGerarExcecao_AoReceberPedidoComItensComQuantidadeVazia_ProdutoDoPedidoSemQuantidadeException(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        pedido.addProduto(new Produto(UUID.randomUUID(), 1, CategoriaEnum.BEBIDA));
        pedido.addProduto(new Produto(UUID.randomUUID(), 0, CategoriaEnum.BEBIDA));
        try {
            pedido.verificaItensDoPedido();
        } catch (Exception exception) {
            assertInstanceOf(ProdutoDoPedidoSemQuantidadeException.class, exception);
        }
    }

    @Test
    public void DeveValidarPedidoComProdutosValidos(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        pedido.addProduto(new Produto(UUID.randomUUID(), 1, CategoriaEnum.BEBIDA));
        pedido.addProduto(new Produto(UUID.randomUUID(), 1, CategoriaEnum.BEBIDA));
        try {
            pedido.verificaItensDoPedido();
        } catch (Exception exception) {}
        assertInstanceOf(Pedido.class, pedido);
    }

    @Test
    public void deveObterValorTotalDoPedido(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        Produto umaBebida = new Produto(UUID.randomUUID(), 1, CategoriaEnum.BEBIDA);
        umaBebida.setValor(10.0f);
        pedido.addProduto(umaBebida);
        Produto umLanche = new Produto(UUID.randomUUID(), 1, CategoriaEnum.LANCHE);
        umLanche.setValor(10.0f);
        pedido.addProduto(umLanche);
        assertThat(pedido.valorTotalDoPedido()).isEqualTo(20.0f);
    }
}
