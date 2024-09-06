package com.fiap.msclienteapi.domain.gateway.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoNaoEncontradoException;

import java.util.List;
import java.util.UUID;


public interface BuscaPedidoInterface {

    Pedido encontraPedidoPorUuid(UUID pedidoUuid, UUID clienteUuid) throws PedidoNaoEncontradoException;

    List<Pedido> findAll();
    
    List<Pedido> retornaTodosPorStatusPedido(StatusPedido statusPedido);

    Pedido encontraPedidoShortPorUuid(UUID pedidoUuid, UUID clienteUuid) throws PedidoNaoEncontradoException;
}
