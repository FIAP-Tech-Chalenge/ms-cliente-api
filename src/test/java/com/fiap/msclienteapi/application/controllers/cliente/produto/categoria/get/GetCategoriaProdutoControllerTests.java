package com.fiap.msclienteapi.application.controllers.cliente.produto.categoria.get;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoriaProdutoControllerTests{

    @Mock
    ProdutoRepository produtoRepository;

    @InjectMocks
    GetCategoriaProdutoController getCategoriaProdutoController;

    @Test
    public void deveRetornarStatus404QuandoNaoHouverProdutosNaDeterminadaCategoria(){
        ResponseEntity<Object> responsePresenter = getCategoriaProdutoController.getProdutoPorCategoria("LANCHE");
        assertThat(responsePresenter.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void deveRetornarStatus200AoEncontrarProdutos(){
        List<ProdutoModel> produtos = new ArrayList<>();
        produtos.add(new ProdutoModel(UUID.randomUUID(), "coca", 10.0f, "refrigerante", CategoriaEnum.BEBIDA, 1));
        when(produtoRepository.findByCategoria(CategoriaEnum.BEBIDA)).thenReturn(produtos);

        ResponseEntity<Object> responsePresenter = getCategoriaProdutoController.getProdutoPorCategoria("BEBIDA");
        assertThat(responsePresenter.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

}