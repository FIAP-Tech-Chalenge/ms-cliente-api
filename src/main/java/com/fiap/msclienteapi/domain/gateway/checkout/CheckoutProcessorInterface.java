package com.fiap.msclienteapi.domain.gateway.checkout;

import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;

public interface CheckoutProcessorInterface {
    Checkout processarCheckout(Pedido pedido);
}
