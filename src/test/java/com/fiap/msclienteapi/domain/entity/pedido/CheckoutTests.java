package com.fiap.msclienteapi.domain.entity.pedido;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.util.UUID;


public class CheckoutTests {
    
    @Test
    public void deveInstanciarCheckoutTests() {
        UUID randomUUID = UUID.randomUUID();
        Checkout checkout = new Checkout(randomUUID, StatusPagamento.PAGO);
        assertInstanceOf(Checkout.class, checkout);
        checkout.setQrCode("qRcODEvALUE");
        assertThat(checkout.getQrCode()).isEqualTo("qRcODEvALUE");
        assertThat(checkout.getUuidPedido()).isEqualTo(randomUUID);
        assertThat(checkout.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
    }
}
