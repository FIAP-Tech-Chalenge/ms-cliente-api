package com.fiap.msclienteapi.infra.queue.kafka.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msclienteapi.domain.gateway.producers.InativarClienteProducerInterface;
import com.fiap.msclienteapi.domain.output.cliente.InativacaoClienteOutput;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaProducerResolver;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaSenderConfig;

import java.util.UUID;

public class InativarClienteProducer extends KafkaSenderConfig implements InativarClienteProducerInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InativarClienteProducer(String servers) {
        super(servers, new KafkaProducerResolver().getClienteInativoProducer());
    }

    public void send(InativacaoClienteOutput clienteOutput) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("cliente_id", clienteOutput.getCliente().getUuid().toString());
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}