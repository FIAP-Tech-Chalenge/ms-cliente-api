package com.fiap.msclienteapi.application.controllers.cliente.pagamento;

import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.pedido.CheckOutOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.pagamento.ProcessarPagamentoPresenter;
import com.fiap.msclienteapi.domain.useCase.checkout.ProcessaCheckoutUseCase;
import com.fiap.msclienteapi.infra.adpter.mock.pagamento.qrCode.MercadoPagoIntegrationMock;
import com.fiap.msclienteapi.infra.adpter.repository.checkout.CheckoutRepository;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import com.fiap.msclienteapi.infra.dependecy.HttpAdapter;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cliente/pagamento")
public class ProcessarPagamentoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @PostMapping("/{uuid}/checkout")
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> processarCheckout(@PathVariable UUID uuid) {
        ProcessaCheckoutUseCase processaCheckoutUseCase = new ProcessaCheckoutUseCase(
                new BuscarPedidoRepository(pedidoRepository, pedidoProdutoRepository),
                new CheckoutRepository(pedidoRepository),
                new MercadoPagoIntegrationMock(new HttpAdapter())
        );
        processaCheckoutUseCase.execute(uuid);
        OutputInterface outputInterface = processaCheckoutUseCase.getCheckoutOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        ProcessarPagamentoPresenter presenter = new ProcessarPagamentoPresenter((CheckOutOutput) outputInterface);

        return new PresenterResponse().response(presenter);
    }
}
