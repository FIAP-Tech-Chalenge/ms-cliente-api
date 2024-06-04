package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer;

public class KafkaProducerResolver {

    public String getPagamentoProducer() {
        return "pagamento";
    }

    public String getClienteProducer() {
        return "cliente";
    }
}
