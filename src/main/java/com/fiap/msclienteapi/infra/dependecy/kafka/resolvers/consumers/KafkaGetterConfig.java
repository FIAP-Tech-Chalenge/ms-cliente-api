package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaGetterConfig {
    private final KafkaConsumer<String, String> producer;
    private final String topic;

    public KafkaGetterConfig(String servers, String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    protected void get(String key, String value) {

    }
}
