package com.fiap.msclienteapi.application.controllers.cliente.pedido.confirma;

import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.pedido.CriaPedidoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.pedido.StorePedidoPresenter;
import com.fiap.msclienteapi.domain.useCase.pedido.ConfrimaEntregaPedidoUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.CriaPedidoRepository;
import com.fiap.msclienteapi.infra.dependecy.StringValidatorsAdapter;
import com.fiap.msclienteapi.infra.dependecy.resolvers.RequestClienteResolver;
import com.fiap.msclienteapi.infra.queue.kafka.producers.PedidoEntregueProducer;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("cliente/pedido-entregue/{pedidoUuid}")
public class ConfirmaPedidoController {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String servers;

    @PostMapping
    @Operation(
            tags = {"cliente"},
            parameters = {
                    @Parameter(name = "Bearer", description = "Authorization header", in = ParameterIn.HEADER)
            }
    )
    ResponseEntity<Object> criaPedido(
            @PathVariable UUID pedidoUuid,
            @RequestParam(name = "cliente_uuid", required = false) UUID queryParamClientUuid,
            HttpServletRequest request
    ) throws Exception {

        String authorizationHeader = request.getHeader("Bearer");

        String uuidClientResolved = RequestClienteResolver.resolve(authorizationHeader, queryParamClientUuid);
        if (!StringValidatorsAdapter.isValidUUID(uuidClientResolved)) {
            throw new Exception("Token de identificação do cliente não encontrado");
        }

        ConfrimaEntregaPedidoUseCase useCase = new ConfrimaEntregaPedidoUseCase(
                new CriaPedidoRepository(pedidoRepository, produtoRepository, pedidoProdutoRepository),
                new BuscarPedidoRepository(pedidoRepository, pedidoProdutoRepository),
                new PedidoEntregueProducer(servers)
        );
        useCase.execute(pedidoUuid, UUID.fromString(uuidClientResolved));
        OutputInterface outputInterface = useCase.getOutputInterface();
        if (outputInterface.getOutputStatus().getCode() != 201) {
            return new GenericResponse().response(outputInterface);
        }
        StorePedidoPresenter presenter = new StorePedidoPresenter((CriaPedidoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}


