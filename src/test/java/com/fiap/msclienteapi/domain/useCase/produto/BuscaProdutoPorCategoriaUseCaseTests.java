package com.fiap.msclienteapi.domain.useCase.produto;


import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscaProdutoPorCategoriaUseCaseTests {

    @Mock
    BuscaProdutoInterface produtoRepository;

    @InjectMocks
    BuscaProdutoPorCategoriaUseCase buscaProdutoPorCategoriaUseCase;

    @Test
    public void deveRetornarOutputDeFalhaAoNaoEncontrarProdutos() {
        String categoria = "BEBIDA";
        List<Produto> produtos = new ArrayList<>();
        
        try {
            when(produtoRepository.encontraProdutoPorCategoria(any(CategoriaEnum.class))).thenReturn(produtos);
            buscaProdutoPorCategoriaUseCase.execute(categoria);
            OutputInterface output = buscaProdutoPorCategoriaUseCase.getBuscaProdutoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not Found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Nenhum Produto na categoria encontrado");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarOutputDeFalhaAoReceberProdutosNull() {
        String categoria = "BEBIDA";        
        try {
            when(produtoRepository.encontraProdutoPorCategoria(any(CategoriaEnum.class))).thenReturn(null);
            buscaProdutoPorCategoriaUseCase.execute(categoria);
            OutputInterface output = buscaProdutoPorCategoriaUseCase.getBuscaProdutoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not Found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Nenhum Produto na categoria encontrado");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarOutputComUmaListaDeProdutosDadaCategorias() {
        String categoria = "BEBIDA";
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("bebida", 10.0f, "bebida", CategoriaEnum.BEBIDA, 1));
        try {
            when(produtoRepository.encontraProdutoPorCategoria(any(CategoriaEnum.class))).thenReturn(produtos);
            buscaProdutoPorCategoriaUseCase.execute(categoria);
            OutputInterface output = buscaProdutoPorCategoriaUseCase.getBuscaProdutoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Lista de produtos");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarOutputDeFalhaCasoCategoriaInvalida() {
        String categoria = "CATEGORIAINVALIDA";

        try {
            buscaProdutoPorCategoriaUseCase.execute(categoria);
            OutputInterface output = buscaProdutoPorCategoriaUseCase.getBuscaProdutoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(400);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Bad request");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Categoria inválida: " + categoria);
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarOutputAoTerFalhaNaPesquisa() {
        String categoria = "BEBIDA";

        try {
            when(produtoRepository.encontraProdutoPorCategoria(any(CategoriaEnum.class))).thenThrow(RuntimeException.class);
            buscaProdutoPorCategoriaUseCase.execute(categoria);
            OutputInterface output = buscaProdutoPorCategoriaUseCase.getBuscaProdutoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Server Error");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Erro no servidor");
        } catch (Exception e) {}
    }

}