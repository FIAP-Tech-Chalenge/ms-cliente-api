package com.fiap.msclienteapi.domain.presenters.cliente.produto;

import com.fiap.msclienteapi.domain.entity.produto.Imagem;
import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.output.produto.BuscaProdutoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProdutoPresenterTests {
    
    @Mock
    BuscaProdutoOutput buscaProdutoOutput;
    
    GetProdutoPresenter getProdutoPresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        getProdutoPresenter = new GetProdutoPresenter(buscaProdutoOutput);
    }

    @Test
    public void deveObterUmOutput() {
        Produto produto = new Produto("coca-cola",10.0f,"refrigerante", CategoriaEnum.BEBIDA, 1);

        when(buscaProdutoOutput.getProduto()).thenReturn(produto);

        var produtoRetornado = getProdutoPresenter.getOutput().getProduto();
        assertThat(produtoRetornado.getNome()).isEqualTo("coca-cola");
    }

    @Test
    void deveRetornarUmArray() {
        Produto produto = new Produto("coca-cola",10.0f,"refrigerante", CategoriaEnum.BEBIDA, 1);
        List<Imagem> imagens = new ArrayList<Imagem>();
        imagens.add(new Imagem("coca-cola.png", "google.com"));
        produto.setImagens(imagens);

        Map<String, Object> produtoComImagens = new HashMap<>();
        produtoComImagens.put("uuid", produto.getUuid());
        produtoComImagens.put("nome", produto.getNome());
        produtoComImagens.put("valor", produto.getValor());
        produtoComImagens.put("descricao", produto.getDescricao());
        produtoComImagens.put("categoria", produto.getCategoria());
        produtoComImagens.put("quantidade", produto.getQuantidade());
        if (produto.getImagens() != null) {
            List<Map<String, Object>> produtoImagensMapList = new ArrayList<>();
            for (Imagem imagem : produto.getImagens()) {
                Map<String, Object> produtoImagemMap = new HashMap<>();
                produtoImagemMap.put("nome", imagem.nome());
                produtoImagemMap.put("url", imagem.url());
                produtoImagensMapList.add(produtoImagemMap);
            }
            produtoComImagens.put("imagens", produtoImagensMapList);
        }

        when(buscaProdutoOutput.getProduto()).thenReturn(produto);

        var produtoComImagemRetornado = getProdutoPresenter.toArray();
        assertThat(produtoComImagemRetornado).isEqualTo(produtoComImagens);
    }

    @Test
    void deveRetornarUmArraySemImagens() {
        Produto produto = new Produto("coca-cola",10.0f,"refrigerante", CategoriaEnum.BEBIDA, 1);
    
        Map<String, Object> produtoComImagens = new HashMap<>();
        produtoComImagens.put("uuid", produto.getUuid());
        produtoComImagens.put("nome", produto.getNome());
        produtoComImagens.put("valor", produto.getValor());
        produtoComImagens.put("descricao", produto.getDescricao());
        produtoComImagens.put("categoria", produto.getCategoria());
        produtoComImagens.put("quantidade", produto.getQuantidade());
        if (produto.getImagens() != null) {
            List<Map<String, Object>> produtoImagensMapList = new ArrayList<>();
            for (Imagem imagem : produto.getImagens()) {
                Map<String, Object> produtoImagemMap = new HashMap<>();
                produtoImagemMap.put("nome", imagem.nome());
                produtoImagemMap.put("url", imagem.url());
                produtoImagensMapList.add(produtoImagemMap);
            }
            produtoComImagens.put("imagens", produtoImagensMapList);
        }

        when(buscaProdutoOutput.getProduto()).thenReturn(produto);

        var produtoComImagemRetornado = getProdutoPresenter.toArray();
        assertThat(produtoComImagemRetornado).isEqualTo(produtoComImagens);
    }
}