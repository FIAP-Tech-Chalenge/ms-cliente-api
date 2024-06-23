package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaSenderConfig {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public KafkaSenderConfig(String servers, String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    protected void send(String key, String value) {
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
