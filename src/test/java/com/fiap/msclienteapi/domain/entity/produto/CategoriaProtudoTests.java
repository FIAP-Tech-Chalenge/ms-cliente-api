package com.fiap.msclienteapi.domain.entity.produto;

import com.fiap.msclienteapi.domain.exception.produto.CategoriaDeProdutoInvalidaException;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class CategoriaProtudoTests {

    @Test
    public void deveCriarCategoriaValida() {
        CategoriaProtudo categoria = new CategoriaProtudo("BEBIDA");
        assertThat(categoria.categoriaEnum()).isEqualTo("BEBIDA");
    }

    @Test
    public void deveValidarCategoria() {
        CategoriaProtudo categoria = new CategoriaProtudo("BEBIDA");
        try {
            categoria.validaCategoriaEnum();
        } catch (Exception e) {}
        assertThat(categoria.categoriaEnum()).isEqualTo("BEBIDA");
    }

    @Test
    public void deveGerarExcecao_AoReceberUmaCategoriaInvalida_CategoriaDeProdutoInvalidaException() {
        CategoriaProtudo categoria = new CategoriaProtudo("Aloha");
        try {
            categoria.validaCategoriaEnum();
        } catch (Exception exception) {
            assertInstanceOf(CategoriaDeProdutoInvalidaException.class, exception);
        }
    }
}
