package com.fiap.msclienteapi.application.controllers.cliente.pagamento.webhock;

import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.pagamento.StatusPagamentoPresenter;
import com.fiap.msclienteapi.domain.useCase.pagamento.MudaStatusPagamentoUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.CriaPedidoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
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
@RequestMapping("cliente/webhook/statusPagamento")
public class WebhookPagamentoAguardandoController {
    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final ProdutoRepository produtoRepository;

    @PostMapping("/{uuid}/pagamento/aguardando")
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> pagamentoAguardandoPagamento(@PathVariable UUID uuid) {
        MudaStatusPagamentoUseCase statusPagamentoUseCase = new MudaStatusPagamentoUseCase(
                new BuscarPedidoRepository(
                        this.pedidoRepository,
                        this.pedidoProdutoRepository
                ),
                new CriaPedidoRepository(
                        this.pedidoRepository,
                        this.produtoRepository,
                        this.pedidoProdutoRepository
                )
        );
        statusPagamentoUseCase.execute(uuid, StatusPagamento.AGUARDANDO_PAGAMENTO);
        OutputInterface outputInterface = statusPagamentoUseCase.getBuscaPedidoOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        StatusPagamentoPresenter presenter = new StatusPagamentoPresenter((StatusPagamentoOutput) outputInterface);

        return new PresenterResponse().response(presenter);
    }
}
