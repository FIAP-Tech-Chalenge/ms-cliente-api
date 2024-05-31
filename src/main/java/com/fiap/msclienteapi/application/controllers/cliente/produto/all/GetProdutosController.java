package com.fiap.msclienteapi.application.controllers.cliente.produto.all;


import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.produto.BuscaTodosProdutoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.produto.GetProdutosPresenter;
import com.fiap.msclienteapi.domain.useCase.produto.BuscaTodosProdutosUseCase;
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
public class GetProdutosController {
    private final ProdutoRepository produtoRepository;

    @GetMapping
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> getAllProdutos() {
        BuscaTodosProdutosUseCase useCase = new BuscaTodosProdutosUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase.execute();
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetProdutosPresenter presenter = new GetProdutosPresenter((BuscaTodosProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
