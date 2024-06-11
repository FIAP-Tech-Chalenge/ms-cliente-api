package com.fiap.msclienteapi.domain.entity.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;

public class ProdutoTests {
    
    @Test
	public void deveInstanciarUmaProduto() {
        Produto produto = new Produto("coca-cola", 10.50f, "uma bebida", CategoriaEnum.BEBIDA, 2);
        assertEquals("coca-cola", produto.getNome());
        assertEquals(10.50f, produto.getValor());
        assertEquals("uma bebida", produto.getDescricao());
        assertEquals(CategoriaEnum.BEBIDA, produto.getCategoria());
        assertEquals(2, produto.getQuantidade());
    }
}
