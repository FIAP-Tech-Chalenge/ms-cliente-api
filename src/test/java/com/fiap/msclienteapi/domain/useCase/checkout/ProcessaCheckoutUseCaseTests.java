package com.fiap.msclienteapi.domain.useCase.checkout;

import com.fiap.msclienteapi.domain.entity.pagamento.GatewayQrCode;
import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.exception.pedido.ClienteNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.checkout.CheckoutProcessorInterface;
import com.fiap.msclienteapi.domain.gateway.pagamento.PagamentoQrCodeInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

public class ProcessaCheckoutUseCaseTests {

    @Mock
    BuscarPedidoRepository buscarPedidoRepository;

    @Mock
    CheckoutProcessorInterface checkoutProcessor;
    
    @Mock
    PagamentoQrCodeInterface pagamentoQrCodeInterface;

    @Mock
    OutputInterface checkoutOutput;

    ProcessaCheckoutUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new ProcessaCheckoutUseCase(buscarPedidoRepository, checkoutProcessor, pagamentoQrCodeInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void devePermitirBuscarPedidoPorUUID() {
        UUID pedidoUuid = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUuid);
        Checkout checkout = new Checkout(pedidoUuid, StatusPagamento.PAGO);
        GatewayQrCode gatewayQrCode = new GatewayQrCode("a", "b");
        
        when(buscarPedidoRepository.encontraPedidoPorUuid(pedidoUuid, null)).thenReturn(pedido);
        when(checkoutProcessor.processarCheckout(pedido)).thenReturn(checkout);
        when(pagamentoQrCodeInterface.geraQrCodePagamento(pedidoUuid, pedido.getTotal())).thenReturn(gatewayQrCode);


        try {
            useCase.execute(pedidoUuid);
            var output = useCase.getCheckoutOutput();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(200);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Ok");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Checkout realizado com sucesso");
        } catch (Exception e) {}
    }

    @Test
    public void deveGerarExcecao_QuandoBuscarClientePorUuid_PedidoNaoEncontradoException() {
        when(buscarPedidoRepository.encontraPedidoPorUuid(UUID.randomUUID(), null)).thenReturn(null);
        try {
            useCase.execute(UUID.randomUUID());
        } catch (Exception e) {
            assertInstanceOf(ClienteNaoEncontradoException.class, e);
        }
    }
}