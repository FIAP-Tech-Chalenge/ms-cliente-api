package com.fiap.msclienteapi.application.controllers.cliente.pedido.get;

import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.pedido.BuscaTodosPedidoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.pedido.AcompanhaPedidosPresenter;
import com.fiap.msclienteapi.domain.useCase.pedido.AcompanhaPedidosUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("cliente/acompanha/")
public class AcompanhaPedidosController {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @GetMapping("/pedidos")
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> getAcompanhaPedidos() throws Exception {

        AcompanhaPedidosUseCase useCase = new AcompanhaPedidosUseCase(new BuscarPedidoRepository(pedidoRepository, pedidoProdutoRepository));
        useCase.execute();
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();
        
        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        AcompanhaPedidosPresenter presenter = new AcompanhaPedidosPresenter((BuscaTodosPedidoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
