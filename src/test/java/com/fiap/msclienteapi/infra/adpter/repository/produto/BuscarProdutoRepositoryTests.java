package com.fiap.msclienteapi.infra.adpter.repository.produto;

import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msclienteapi.infra.model.ProdutoModel;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarProdutoRepositoryTests {

    @Mock
    ProdutoRepository produtoRepository;

    @InjectMocks
    BuscarProdutoRepository buscarProdutoRepository;

    @Test
    public void deveEncontrarUmProdutoPorUUID(){
        UUID produtoUUID = UUID.randomUUID();
        ProdutoModel produtoModel = new ProdutoModel(produtoUUID, "guarana", 10.0f, "guarana", CategoriaEnum.BEBIDA, 1);

        when(produtoRepository.findByUuid(produtoUUID)).thenReturn(produtoModel);
        
        try {
            Produto produtoRetornado = buscarProdutoRepository.encontraProdutoPorUuid(produtoUUID);
            assertThat(produtoRetornado.getNome()).isEqualTo(produtoModel.getNome());
            assertThat(produtoRetornado.getDescricao()).isEqualTo(produtoModel.getDescricao());
            assertThat(produtoRetornado.getQuantidade()).isEqualTo(produtoModel.getQuantidade());
            assertThat(produtoRetornado.getValor()).isEqualTo(produtoModel.getValor());
        } catch (Exception e) {}
    }

    @Test
    public void deveGerarExcecao_AoBuscarPorUUIDeNaoEncontrarValor_ProdutoNaoEncontradoException(){
        UUID randomUUID = UUID.randomUUID();
        when(produtoRepository.findByUuid(randomUUID)).thenReturn(null);
        try {
            buscarProdutoRepository.encontraProdutoPorUuid(randomUUID);
        } catch (Exception e) {
            assertInstanceOf(ProdutoNaoEncontradoException.class, e);
            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }

    @Test
    public void deveRetornarTodosOsProdutos(){
        List<ProdutoModel> produtosModel = new ArrayList<>();
        produtosModel.add(new ProdutoModel(null, null, null, null, null, null));

        when(produtoRepository.findAll()).thenReturn(produtosModel);
        List<Produto> produtos = buscarProdutoRepository.findAll();

        assertThat(produtos).isNotEmpty();
    }

    @Test
    public void deveBuscaProdutosPorCategoria(){
        List<ProdutoModel> produtosModel = new ArrayList<>();
        produtosModel.add(new ProdutoModel(null, null, null, null, null, null));

        when(produtoRepository.findByCategoria(any(CategoriaEnum.class))).thenReturn(produtosModel);
        try {
            List<Produto> produtos = buscarProdutoRepository.encontraProdutoPorCategoria(CategoriaEnum.ACOMPANHAMENTO);
            assertThat(produtos).isNotEmpty();
        } catch (Exception e) {}
    }

    @Test
    public void deveGerarExcecao_CasoProdutoNaoExista_ProdutoNaoEncontradoException(){
        List<ProdutoModel> produtosModel = new ArrayList<>();

        when(produtoRepository.findByCategoria(any(CategoriaEnum.class))).thenReturn(produtosModel);
        
        try {
            buscarProdutoRepository.encontraProdutoPorCategoria(CategoriaEnum.ACOMPANHAMENTO);
        } catch (Exception e) {
            assertInstanceOf(ProdutoNaoEncontradoException.class, e);
            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }


}