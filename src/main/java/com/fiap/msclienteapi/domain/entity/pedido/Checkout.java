package com.fiap.msclienteapi.domain.entity.pedido;

import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class Checkout {
    private final StatusPagamento statusPagamento;
    private final UUID uuidPedido;
    private String qrCode;

    public Checkout(UUID uuidPedido, StatusPagamento statusPagamento) {
        this.uuidPedido = uuidPedido;
        this.statusPagamento = statusPagamento;
    }
}
