package com.fiap.msclienteapi.infra.queue.kafka.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msclienteapi.domain.gateway.producers.PagamentoProducerInterface;
import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaProducerResolver;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaSenderConfig;

import java.util.UUID;

public class PagamentoAprovadoProducer extends KafkaSenderConfig implements PagamentoProducerInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PagamentoAprovadoProducer(String servers) {
        super(servers, new KafkaProducerResolver().getPagamentoProducer());
    }

    @Override
    public void send(StatusPagamentoOutput statusPagamentoOutput) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("pedido_uuid", statusPagamentoOutput.getPedidoEntity().getUuid().toString());
            jsonNode.put("cliente_uuid", statusPagamentoOutput.getPedidoEntity().getClienteUuid().toString());
            jsonNode.put("status_pagamento", statusPagamentoOutput.getPedidoEntity().getStatusPagamento().toString());
            jsonNode.put("numero_pedido", statusPagamentoOutput.getPedidoEntity().getNumeroPedido());
            jsonNode.put("total", statusPagamentoOutput.getPedidoEntity().getTotal());
            jsonNode.put("produtos", statusPagamentoOutput.getPedidoEntity().getProdutos().toString());
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
