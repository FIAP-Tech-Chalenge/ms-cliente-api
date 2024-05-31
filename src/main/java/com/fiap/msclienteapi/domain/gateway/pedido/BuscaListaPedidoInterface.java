package com.fiap.msclienteapi.domain.gateway.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;

import java.util.List;

public interface BuscaListaPedidoInterface {
    List<Pedido> findListaPedidos();
}
