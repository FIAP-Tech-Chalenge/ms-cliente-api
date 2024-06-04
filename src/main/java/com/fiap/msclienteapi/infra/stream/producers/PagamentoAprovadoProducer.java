package com.fiap.msclienteapi.infra.stream.producers;

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
    public void send(StatusPagamentoOutput message) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            //jsonNode.put("cliente_id", clienteOutput.getUuid().toString());
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
