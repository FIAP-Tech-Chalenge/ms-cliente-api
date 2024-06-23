package com.fiap.msclienteapi.infra.queue.kafka.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msclienteapi.domain.gateway.producers.PedidoProducerInterface;
import com.fiap.msclienteapi.domain.generic.output.PedidoOutput;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaProducerResolver;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaSenderConfig;

import java.util.UUID;

public class PedidoProducer extends KafkaSenderConfig implements PedidoProducerInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PedidoProducer(String servers) {
        super(servers, new KafkaProducerResolver().getPedidoProducer());
    }

    public void send(PedidoOutput pedidoOutput) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("total", pedidoOutput.getTotal());
            jsonNode.put("uuid_pedido", pedidoOutput.getUuid_pedido().toString());
            jsonNode.put("status_pedido", pedidoOutput.getStatus_pedido().toString());
            jsonNode.put("numero_pedido", pedidoOutput.getNumero_pedido());
            jsonNode.put("status_pagamento", pedidoOutput.getStatus_pedido().toString());
            jsonNode.put("cliente_uuid", pedidoOutput.getCliente_uuid().toString());
            String produtosJson = objectMapper.writeValueAsString(pedidoOutput.getProdutos());
            jsonNode.put("produtos", produtosJson);
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}