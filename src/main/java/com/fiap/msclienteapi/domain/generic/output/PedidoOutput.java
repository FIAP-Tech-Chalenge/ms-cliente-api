package com.fiap.msclienteapi.domain.generic.output;

import com.fiap.msclienteapi.domain.entity.pedido.Produto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PedidoOutput implements OutputInterface{
    private double total;
    private UUID uuid_pedido;
    private String status_pedido;
    private List<Produto> produtos;
    private int numero_pedido;
    private String status_pagamento;
    private UUID cliente_uuid;

    public PedidoOutput(double total, UUID uuid_pedido, String status_pedido, List<Produto> produtos, int numero_pedido, String status_pagamento, UUID cliente_uuid) {
        this.total = total;
        this.uuid_pedido = uuid_pedido;
        this.status_pedido = status_pedido;
        this.produtos = produtos;
        this.numero_pedido = numero_pedido;
        this.status_pagamento = status_pagamento;
        this.cliente_uuid = cliente_uuid;
    }

    @Override
    public Object getBody() {
        return this;
    }

    @Override
    public OutputStatus getOutputStatus() {
        return new OutputStatus(200, "Ok", "Pedido encontrado");
    }


}




