package com.fiap.msclienteapi.application.controllers.cliente.produto.get;

import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.infra.model.ProdutoModel;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProdutoControllerTests {

    @Mock
    ProdutoRepository produtoRepository;

    @InjectMocks
    GetProdutoController getProdutoController;

    @Test    
    public void deveRetornar404AoNaoEncontrarProduto(){
        ResponseEntity<Object> responsePresenter = getProdutoController.getProduto(UUID.randomUUID());
        assertThat(responsePresenter.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test    
    public void deveRetornar200AoEncontrarProduto(){
        UUID produtoUUID = UUID.randomUUID();
        ProdutoModel produto = new ProdutoModel(produtoUUID, "coca", 10.0f, "refri", CategoriaEnum.BEBIDA, 1);
        when(produtoRepository.findByUuid(produtoUUID)).thenReturn(produto);

        ResponseEntity<Object> responsePresenter = getProdutoController.getProduto(produtoUUID);
        assertThat(responsePresenter.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }


}