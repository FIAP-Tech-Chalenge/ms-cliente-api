package com.fiap.msclienteapi.domain.input.pedido;


import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;

import java.util.UUID;

public record ProdutoPedidoInput(UUID uuid, Integer quantidade, CategoriaEnum categoria) {
}
