package com.fiap.msclienteapi.application.controllers.cliente.pedido.store.requests;

import com.fiap.msclienteapi.domain.input.pedido.ProdutoPedidoInput;

import java.util.List;
import java.util.UUID;

public record StorePedidoRequest(UUID clienteUuid, List<ProdutoPedidoInput> produtoList, Long numeroPedido) {
}
