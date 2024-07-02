package com.fiap.msclienteapi.domain.useCase.produto;

import com.fiap.msclienteapi.domain.entity.produto.Produto;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscaTodosProdutosUseCaseTests {

    @Mock
    BuscaProdutoInterface buscaProdutoInterface;

    @InjectMocks
    BuscaTodosProdutosUseCase buscaTodosProdutosUseCase;

    @Test
    public void deveRetornarOutputSucessoAoEncontrarProduto() {
        List<Produto> listProdutos = new ArrayList<>();

        when(buscaProdutoInterface.findAll()).thenReturn(listProdutos);
        buscaTodosProdutosUseCase.execute();
        OutputInterface output = buscaTodosProdutosUseCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
        assertThat(output.getOutputStatus().getMessage()).isEqualTo("Lista de produtos");
    }

    @Test
    public void deveRetornarOutputFalhaAoFalharBusca() {
        when(buscaProdutoInterface.findAll()).thenThrow(RuntimeException.class);
        buscaTodosProdutosUseCase.execute();
        OutputInterface output = buscaTodosProdutosUseCase.getBuscaProdutoOutput();

        assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage()).isEqualTo("Erro no servidor");
    }

}