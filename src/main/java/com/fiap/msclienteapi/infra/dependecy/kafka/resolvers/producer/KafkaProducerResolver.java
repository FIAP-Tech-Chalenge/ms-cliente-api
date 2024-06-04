package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer;

import org.springframework.beans.factory.annotation.Value;

public class KafkaProducerResolver {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private static String servers;

    public static ObjectProducer getPagamentoProducer() {
        return new ObjectProducer(servers, "pagamento");
    }

    public static ObjectProducer getClienteProducer() {
        return new ObjectProducer(servers, "cliente");
    }
}
