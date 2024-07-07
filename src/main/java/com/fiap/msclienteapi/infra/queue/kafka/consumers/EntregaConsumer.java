package com.fiap.msclienteapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers.KafkaConsumerResolver;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@Component
public class EntregaConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final PedidoRepository pedidoRepository;

    public EntregaConsumer(Properties kafkaConsumerProperties,
                           PedidoRepository pedidoRepository) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getEntregaConsumer()));
        this.objectMapper = new ObjectMapper();
        this.pedidoRepository = pedidoRepository;
    }


    public void runConsumer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Mensagem recebida - Tópico: %s, Chave: %s, Valor: %s%n", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String uuid = messageJson.get("uuid_pedido").asText();
                        Long numero = messageJson.get("numero_pedido").asLong();
                        String status = messageJson.get("status_pedido").asText();

                        PedidoModel pedidoModel = pedidoRepository.findById(UUID.fromString(uuid)).orElse(null);

                        if (pedidoModel != null) {
                            pedidoModel.setStatusPedido(StatusPedido.valueOf(status));
                            pedidoModel.setNumeroPedido(numero);
                            pedidoRepository.save(pedidoModel);
                        }else {
                            System.err.println("Pedido não encontrado: " + uuid);
                        }

                    } catch (Exception e) {
                        System.err.println("Erro ao processar a mensagem: " + e.getMessage());
                    }
                }
            }
        } finally {
            this.consumer.close();
            System.out.println("Consumidor Kafka fechado.");
        }
    }
}
