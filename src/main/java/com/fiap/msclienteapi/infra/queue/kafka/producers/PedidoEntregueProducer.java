package com.fiap.msclienteapi.infra.queue.kafka.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msclienteapi.domain.gateway.producers.PedidoEntregueProducerInterface;
import com.fiap.msclienteapi.domain.output.pedido.CriaPedidoOutput;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaProducerResolver;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaSenderConfig;

import java.util.UUID;

public class PedidoEntregueProducer extends KafkaSenderConfig implements PedidoEntregueProducerInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PedidoEntregueProducer(String servers) {
        super(servers, new KafkaProducerResolver().getPedidoEntregeProducer());
    }
    @Override
    public void send(CriaPedidoOutput statusPagamentoOutput) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("pedido_uuid", statusPagamentoOutput.getPedido().getUuid().toString());
            jsonNode.put("cliente_uuid", statusPagamentoOutput.getPedido().getClienteUuid().toString());
            jsonNode.put("numero_pedido", statusPagamentoOutput.getPedido().getNumeroPedido());
            jsonNode.put("status_pedido", statusPagamentoOutput.getPedido().getStatusPedido().toString());
            jsonNode.put("total", statusPagamentoOutput.getPedido().getTotal());

            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
