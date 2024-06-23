package com.fiap.msclienteapi.domain.presenters.cliente.produto;

import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.output.produto.BuscaTodosProdutoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProdutosPresenterTests {

    @Mock
    BuscaTodosProdutoOutput buscaTodosProdutoOutput;
    
    GetProdutosPresenter getProdutosPresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        getProdutosPresenter = new GetProdutosPresenter(buscaTodosProdutoOutput);
    }

    @Test
    public void deveObterUmOutput() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("coca-cola",10.0f,"refrigerante", CategoriaEnum.BEBIDA, 1));

        when(buscaTodosProdutoOutput.getListProdutos()).thenReturn(produtos);

        var listaProdutosRetornada = getProdutosPresenter.getOutput().getListProdutos();
        assertThat(listaProdutosRetornada).isNotNull();
    }

    @Test
    void deveRetornarUmArray() {

        // TODO: Reimplementar teste

        // List<Produto> produtos = new ArrayList<>();
        // Produto novoProduto = new Produto("coca-cola",10.0f,"refrigerante", CategoriaEnum.BEBIDA, 1);
        // novoProduto.setUuid(UUID.randomUUID());
        // produtos.add(novoProduto);

        // Map<String, Object> retornoPedidos = new HashMap<>();
        // List<Map<String, Object>> produtosMapList = new ArrayList<>();

        // for (Produto produto : produtos) {
        //     Map<String, Object> pedidoMap = new HashMap<>();
        //     pedidoMap.put("uuid", produto.getUuid().toString());
        //     pedidoMap.put("nome", produto.getNome());
        //     pedidoMap.put("descricao", produto.getDescricao());
        //     pedidoMap.put("categoria", produto.getCategoria());
        //     pedidoMap.put("quantidade", produto.getQuantidade());
            
        //     produtosMapList.add(pedidoMap);
        // }

        // retornoPedidos.put("pedidos", produtosMapList);

        // when(buscaTodosProdutoOutput.getListProdutos()).thenReturn(produtos);

        // var produtosRetornados = getProdutosPresenter.toArray();
        // assertThat(produtosRetornados).isEqualTo(retornoPedidos);
    }
}