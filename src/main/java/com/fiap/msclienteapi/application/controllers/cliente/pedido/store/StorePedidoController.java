package com.fiap.msclienteapi.application.controllers.cliente.pedido.store;

import com.fiap.msclienteapi.application.controllers.cliente.pedido.store.requests.StorePedidoRequest;
import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.input.pedido.CriarPedidoInput;
import com.fiap.msclienteapi.domain.output.pedido.CriaPedidoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.pedido.StorePedidoPresenter;
import com.fiap.msclienteapi.domain.useCase.pedido.CriaPedidoUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.cliente.ClienteEntityRepository;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.CriaPedidoRepository;
import com.fiap.msclienteapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msclienteapi.infra.dependecy.StringValidatorsAdapter;
import com.fiap.msclienteapi.infra.dependecy.resolvers.RequestClienteResolver;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("cliente/pedido")
public class StorePedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @PostMapping
    @Operation(
            tags = {"cliente"},
            parameters = {
                    @Parameter(name = "Bearer", description = "Authorization header", in = ParameterIn.HEADER)
            }
    )
    ResponseEntity<Object> criaPedido(
            @RequestBody StorePedidoRequest criarProdutoRequest,
            HttpServletRequest request
    ) throws Exception {
        String authorizationHeader = request.getHeader("Bearer");
        String uuidClientResolvedString = RequestClienteResolver.resolve(
                authorizationHeader,
                criarProdutoRequest.clienteUuid()
        );
        if (!StringValidatorsAdapter.isValidUUID(uuidClientResolvedString)) {
            throw new Exception("Token de identificação do cliente não encontrado");
        }
        UUID uuidClientResolved = StringValidatorsAdapter.toUUID(uuidClientResolvedString);

        CriarPedidoInput criaPedidoInput = new CriarPedidoInput(
                uuidClientResolved,
                criarProdutoRequest.produtoList(),
                criarProdutoRequest.numeroPedido()
        );
        CriaPedidoUseCase useCase = new CriaPedidoUseCase(
                new CriaPedidoRepository(pedidoRepository, produtoRepository, pedidoProdutoRepository),
                new ClienteEntityRepository(clienteRepository),
                new BuscarProdutoRepository(produtoRepository)
        );
        useCase.execute(criaPedidoInput);
        OutputInterface outputInterface = useCase.getCriaPedidoOutput();
        if (outputInterface.getOutputStatus().getCode() != 201) {
            return new GenericResponse().response(outputInterface);
        }
        StorePedidoPresenter presenter = new StorePedidoPresenter((CriaPedidoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}


