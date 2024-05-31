package com.fiap.msclienteapi.domain.gateway.produto;

import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.exception.produto.ProdutoNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public interface BuscaProdutoInterface {
    Produto encontraProdutoPorUuid(UUID uuid) throws ProdutoNaoEncontradoException;

    List<Produto> findAll();

    List<Produto> encontraProdutoPorCategoria(CategoriaEnum categoria) throws ProdutoNaoEncontradoException;
}