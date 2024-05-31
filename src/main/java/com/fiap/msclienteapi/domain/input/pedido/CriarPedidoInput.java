package com.fiap.msclienteapi.domain.input.pedido;

import java.util.List;
import java.util.UUID;

public record CriarPedidoInput(UUID clienteUuid, List<ProdutoPedidoInput> produtoList, Long numeroPedido) {
}
