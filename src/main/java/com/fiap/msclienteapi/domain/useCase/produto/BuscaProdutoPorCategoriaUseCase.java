package com.fiap.msclienteapi.domain.useCase.produto;


import com.fiap.msclienteapi.domain.entity.produto.CategoriaProtudo;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.exception.produto.CategoriaDeProdutoInvalidaException;
import com.fiap.msclienteapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.produto.BuscaTodosProdutoOutput;
import com.fiap.msclienteapi.domain.gateway.produto.BuscaProdutoInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class BuscaProdutoPorCategoriaUseCase {

    private final BuscaProdutoInterface produtoRepository;
    private OutputInterface buscaProdutoOutput;

    public void execute(String categoria) {
        try {
            CategoriaProtudo produtoEntity = new CategoriaProtudo(categoria);
            CategoriaEnum categoriaEnum= produtoEntity.validaCategoriaEnum();

            List<Produto> listProdutos = this.produtoRepository.encontraProdutoPorCategoria(categoriaEnum);

            if (listProdutos == null || listProdutos.isEmpty()) {
                throw new ProdutoNaoEncontradoException("Nenhum Produto na categoria encontrado");
            }

            buscaProdutoOutput = new BuscaTodosProdutoOutput(
                listProdutos,
                new OutputStatus(200, "OK", "Lista de produtos")
            );
        } catch (ProdutoNaoEncontradoException e) {
            this.buscaProdutoOutput = new OutputError(
                e.getMessage(),
                new OutputStatus(404, "Not Found", e.getMessage())
            );

        } catch (CategoriaDeProdutoInvalidaException e) {
            this.buscaProdutoOutput = new OutputError(
                e.getMessage(),
                new OutputStatus(400, "Bad request", e.getMessage())
            );
        }
        catch (Exception e) {
            buscaProdutoOutput = new OutputError(
                e.getMessage(),
                new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}