package com.fiap.msclienteapi.domain.useCase.pagamento;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.domain.gateway.pedido.PedidoInterface;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class MudaStatusPagamentoUseCaseTests {

    @Mock
    BuscaPedidoInterface buscaPedidoInterface;

    @Mock
    PedidoInterface pedidoInterface;

    private MudaStatusPagamentoUseCase mudaStatusPagamentoUseCase;
    
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        mudaStatusPagamentoUseCase = new MudaStatusPagamentoUseCase(buscaPedidoInterface, pedidoInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void deveAtualizarOStatusDoPagamentoDoPedido() {
        UUID pedidoUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID, null, StatusPedido.PRONTO, StatusPagamento.NAO_PAGO, 20, 10.0f);
        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUUID, null)).thenReturn(pedido);
            when(pedidoInterface.atualizaPagamento(any(Pedido.class), eq(StatusPagamento.PAGO)))
                .thenAnswer(invocation -> {
                    Pedido p = invocation.getArgument(0);
                    p.setStatusPagamento(StatusPagamento.PAGO);
                    return p;
            });
            mudaStatusPagamentoUseCase.execute(pedidoUUID, StatusPagamento.PAGO);
            var output = mudaStatusPagamentoUseCase.getBuscaPedidoOutput();             
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(200);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("OK");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Status do pagamento atualizado");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornar404_AoLancarExcecao_PedidoNaoEncontradoException() {
        UUID pedidoUUID = UUID.randomUUID();
        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUUID, null)).thenThrow(PedidoNaoEncontradoException.class);
            mudaStatusPagamentoUseCase.execute(pedidoUUID, StatusPagamento.PAGO);
            var output = mudaStatusPagamentoUseCase.getBuscaPedidoOutput();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(404);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Not Found");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Pedido não encontrado");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornar500_AoLancarExcecao_Exception() {
        UUID pedidoUUID = UUID.randomUUID();
        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUUID, null)).thenThrow(RuntimeException.class);
            mudaStatusPagamentoUseCase.execute(pedidoUUID, StatusPagamento.PAGO);
            var output = mudaStatusPagamentoUseCase.getBuscaPedidoOutput();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(500);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Internal Server Error");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Erro no servidor");
        } catch (Exception e) {}
    }
}