package com.fiap.msclienteapi.domain.presenters.cliente.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.generic.presenter.PresenterInterface;
import com.fiap.msclienteapi.domain.output.pedido.BuscaTodosPedidoOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcompanhaPedidosPresenter implements PresenterInterface{
    private final BuscaTodosPedidoOutput buscaTodosPedidosOutput;

    public AcompanhaPedidosPresenter(BuscaTodosPedidoOutput buscaPedidoOutput) {
        this.buscaTodosPedidosOutput = buscaPedidoOutput;
    }

    public Map<String, Object> toArray() {
        Map<String, Object> array = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<Pedido> pedidos = (List<Pedido>) this.buscaTodosPedidosOutput.getBody();
        List<Map<String, Object>> produtosMapList = new ArrayList<>();
        pedidos.stream().forEach(pedido -> {
            Map<String, Object> pedidoMap = new HashMap<>();
            pedidoMap.put("pedido_uuid", pedido.getUuid());
            pedidoMap.put("numero_pedido", pedido.getNumeroPedido());
            pedidoMap.put("status_pedido", pedido.getStatusPedido());
            pedidoMap.put("tempo_espera", pedido.getTempoDePreparoEmMinutos());
            produtosMapList.add(pedidoMap);
        });
        array.put("pedidos", produtosMapList);
        return array;
    }

    public BuscaTodosPedidoOutput getOutput() {
        return this.buscaTodosPedidosOutput;
    }
    
}
