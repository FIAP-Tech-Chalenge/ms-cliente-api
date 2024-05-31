package com.fiap.msclienteapi.application.controllers.cliente.produto.get;


import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.produto.BuscaProdutoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.produto.GetProdutoPresenter;
import com.fiap.msclienteapi.domain.useCase.produto.BuscaProdutoPorUuidUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cliente/produto")
public class GetProdutoController {
    private final ProdutoRepository produtoRepository;

    @GetMapping("/{uuid}")
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> getProduto(@PathVariable UUID uuid) {
        BuscaProdutoPorUuidUseCase useCase = new BuscaProdutoPorUuidUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase.execute(uuid);
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();
        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetProdutoPresenter presenter = new GetProdutoPresenter((BuscaProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
