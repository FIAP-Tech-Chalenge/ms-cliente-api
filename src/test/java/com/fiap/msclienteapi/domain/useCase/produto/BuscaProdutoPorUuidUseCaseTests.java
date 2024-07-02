package com.fiap.msclienteapi.domain.useCase.produto;

import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscaProdutoPorUuidUseCaseTests {

    @Mock
    BuscaProdutoInterface buscaProduto;

    @InjectMocks
    BuscaProdutoPorUuidUseCase buscaProdutoPorUuidUseCase;

    @Test
    public void deveRetornarUmOutputDeSucessoAoEncontrarProdutoPorUiid() {
        UUID produtoUUID = UUID.randomUUID();
        Produto produto = new Produto(null, null, null, null, null);

        try {
            when(buscaProduto.encontraProdutoPorUuid(produtoUUID)).thenReturn(produto);
            buscaProdutoPorUuidUseCase.execute(produtoUUID);
            OutputInterface output = buscaProdutoPorUuidUseCase.getBuscaProdutoOutput();

            assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Produto encontrado com sucesso");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarUmOutputDeFalhaAoNaoEncontrarProduto() {
        UUID produtoUUID = UUID.randomUUID();

        try {
            when(buscaProduto.encontraProdutoPorUuid(produtoUUID)).thenThrow(ProdutoNaoEncontradoException.class);
            buscaProdutoPorUuidUseCase.execute(produtoUUID);
            OutputInterface output = buscaProdutoPorUuidUseCase.getBuscaProdutoOutput();

            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not Found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Produto não encontrado");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarUmOutputDeFalhaAoNaoFalharNaPesquisa() {
        UUID produtoUUID = UUID.randomUUID();

        try {
            when(buscaProduto.encontraProdutoPorUuid(produtoUUID)).thenThrow(RuntimeException.class);
            buscaProdutoPorUuidUseCase.execute(produtoUUID);
            OutputInterface output = buscaProdutoPorUuidUseCase.getBuscaProdutoOutput();

            assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Server Error");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Erro no servidor");
        } catch (Exception e) {}
    }

}