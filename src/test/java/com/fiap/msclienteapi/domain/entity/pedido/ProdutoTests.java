package com.fiap.msclienteapi.domain.entity.pedido;

import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.UUID;

public class ProdutoTests {
    
    @Test
    public void deveInstanciarUmProdutoDiretamente(){
        UUID produtoUUID = UUID.randomUUID();
        Produto produto = new Produto(produtoUUID, 2, CategoriaEnum.BEBIDA);
        produto.setCategoria(CategoriaEnum.LANCHE);
        assertThat(produto.getUuid()).isEqualTo(produtoUUID);   
        assertThat(produto.getQuantidade()).isEqualTo(2);   
        assertThat(produto.getCategoria()).isEqualTo(CategoriaEnum.LANCHE);   
    }

    @Test
    public void deveCompararDoisItensIguais(){
        UUID produtoUUID = UUID.randomUUID();
        Produto produto = new Produto(produtoUUID, 2, CategoriaEnum.BEBIDA);
        Produto outroProduto = new Produto(produtoUUID, 2, CategoriaEnum.BEBIDA);
        assertTrue(produto.equals(outroProduto));
        assertTrue(produto.canEqual(outroProduto));
    }

    @Test
    public void deveCompararDoisItensDiferentes(){
        Produto produto = new Produto(UUID.randomUUID(), 2, CategoriaEnum.BEBIDA);
        Produto outroProduto = new Produto(UUID.randomUUID(), 1, CategoriaEnum.ACOMPANHAMENTO);
        assertFalse(produto.equals(outroProduto));
    }
}
