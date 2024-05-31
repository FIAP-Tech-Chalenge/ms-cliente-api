package com.fiap.msclienteapi.domain.gateway.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.exception.produto.ProdutoNaoEncontradoException;

public interface PedidoInterface {
    Pedido criaPedido(Pedido pedido) throws ProdutoNaoEncontradoException;

    Pedido atualizaPagamento(Pedido pedido, StatusPagamento statusPagamento);

}
