package com.fiap.msclienteapi.application.controllers.cliente.produto.all;

import com.fiap.msclienteapi.domain.useCase.produto.BuscaTodosProdutosUseCase;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProdutosControllerTests {
    
    @Mock    
    ProdutoRepository produtoRepository;

    @Mock 
    BuscaTodosProdutosUseCase buscaTodosProdutosUseCase;
    
    @InjectMocks
    GetProdutosController getProdutosController;

    @Test
    public void deveRetornarResponseComSucesso(){
        ResponseEntity<Object> responsePresenter = getProdutosController.getAllProdutos();
        assertThat(responsePresenter.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    public void deveRetornarErroAoNaoEncontrarPedidos(){
        when(produtoRepository.findAll()).thenReturn(null);
        ResponseEntity<Object> responsePresenter = getProdutosController.getAllProdutos();
        assertThat(responsePresenter.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }
}