package com.fiap.msclienteapi.application.controllers.cliente.produto.categoria.get;


import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.produto.BuscaTodosProdutoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.produto.GetProdutosPresenter;
import com.fiap.msclienteapi.domain.useCase.produto.BuscaProdutoPorCategoriaUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("cliente/produto")
public class GetCategoriaProdutoController {
    private final ProdutoRepository produtoRepository;

    @GetMapping("/categoria/{categoria}")
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> getProdutoPorCategoria(String categoria) {
        BuscaProdutoPorCategoriaUseCase useCase = new BuscaProdutoPorCategoriaUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase.execute(categoria);
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetProdutosPresenter presenter = new GetProdutosPresenter((BuscaTodosProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
