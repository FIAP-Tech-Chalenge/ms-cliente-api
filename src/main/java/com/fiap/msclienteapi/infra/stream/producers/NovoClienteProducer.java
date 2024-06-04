package com.fiap.msclienteapi.infra.stream.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msclienteapi.domain.gateway.producers.NovoClienteProducertInterface;
import com.fiap.msclienteapi.domain.generic.output.ClienteOutput;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaProducerResolver;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer.KafkaSenderConfig;

import java.util.UUID;

public class NovoClienteProducer extends KafkaSenderConfig implements NovoClienteProducertInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NovoClienteProducer(String servers) {
        super(servers, new KafkaProducerResolver().getClienteProducer());
    }

    public void send(ClienteOutput clienteOutput) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("cliente_id", clienteOutput.getUuid().toString());
            jsonNode.put("cliente_nome", clienteOutput.getNome());
            jsonNode.put("cliente_cpf", clienteOutput.getCpf());
            jsonNode.put("cliente_email", clienteOutput.getEmail());
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}