package com.fiap.msclienteapi.infra.stream.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msclienteapi.domain.gateway.producers.NovoClienteProducertInterface;
import com.fiap.msclienteapi.domain.generic.output.ClienteOutput;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class NovoClienteProducer implements NovoClienteProducertInterface {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public NovoClienteProducer(String servers, String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void send(OutputInterface message) {
        if (!(message instanceof ClienteOutput)) {
            throw new IllegalArgumentException("A mensagem deve ser uma instância de ClienteOutput");
        }

        ClienteOutput clienteOutput = (ClienteOutput) message;
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("clienteId", clienteOutput.getUuid().toString());
            jsonNode.put("clienteNome", clienteOutput.getNome());
            jsonNode.put("clienteCpf", clienteOutput.getCpf());
            jsonNode.put("clienteEmail", clienteOutput.getEmail());
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(String key, String value) {
        System.out.println("Conteúdo da mensagem: " + value); // Imprime o conteúdo da mensagem
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println("Mensagem enviada com sucesso para o tópico: " + metadata.topic()
                            + "\nPartition: " + metadata.partition()
                            + "\nOffset: " + metadata.offset()
                            + "\nTimestamp: " + metadata.timestamp());
                } else {
                    System.err.println("Erro ao enviar mensagem: " + exception.getMessage());
                }
            }
        });
    }
}