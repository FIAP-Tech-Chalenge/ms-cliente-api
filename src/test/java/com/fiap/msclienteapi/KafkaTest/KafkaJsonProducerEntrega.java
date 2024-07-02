package com.fiap.msclienteapi.KafkaTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.KafkaTopicsEnum;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class KafkaJsonProducerEntrega {

    public static void main(String[] args) {
        String topicName = KafkaTopicsEnum.entrega.name(); // alterado para o tópico de entrega
        String bootstrapServers = "localhost:9092"; // substitua pelo endereço do seu broker Kafka

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (int i = 0; i < 10; i++) {
                String key = "key-" + i;

                // Crie um mapa para simular o JSON
                Map<String, Object> valueMap = new HashMap<>();
                valueMap.put("uuid_pedido", UUID.randomUUID().toString()); // alterado para pedido_uuid
                valueMap.put("numero_pedido", i); // adicionado numero_pedido
                valueMap.put("status_pedido", StatusPedido.FINALIZADO); // adicionado status_pedido

                // Converta o mapa para uma string JSON
                String value = objectMapper.writeValueAsString(valueMap);

                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

                RecordMetadata metadata = producer.send(record).get();
                System.out.printf("Sent record with key %s and value %s to partition %d with offset %d%n",
                        key, value, metadata.partition(), metadata.offset());
            }
        } catch (ExecutionException | InterruptedException | com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
