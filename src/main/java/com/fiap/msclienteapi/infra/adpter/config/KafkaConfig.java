package com.fiap.msclienteapi.infra.adpter.config;

import com.fiap.msclienteapi.infra.stream.producers.NovoClienteProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    @Bean
    public NovoClienteProducer novoClienteProducer() {
        return new NovoClienteProducer(bootstrapServers, topic);
    }
}