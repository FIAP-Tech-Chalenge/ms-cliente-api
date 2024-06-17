package com.fiap.msclienteapi.infra.adpter.repository.checkout;

import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckoutRepositoryTests {

    @Mock
    PedidoRepository pedidoRepository;

    @InjectMocks
    CheckoutRepository checkoutRepository;


    @Test
    public void deveAlterarOValorDoStatusDoPedidoParaAGUARDANDO_PAGAMENTO() {
        UUID pedidoUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID);
        pedido.setUuid(pedidoUUID);
        PedidoModel pedidoModel = new PedidoModel(pedidoUUID, null, UUID.randomUUID(), null, StatusPedido.RECEBIDO, StatusPagamento.NAO_PAGO, 10.0f);
        when(pedidoRepository.findByUuid(pedidoUUID)).thenReturn(pedidoModel);
        Checkout checkout = checkoutRepository.processarCheckout(pedido);
        assertThat(checkout.getUuidPedido()).isEqualTo(pedido.getUuid());
        assertThat(checkout.getStatusPagamento()).isEqualTo(StatusPagamento.AGUARDANDO_PAGAMENTO);
    }
}