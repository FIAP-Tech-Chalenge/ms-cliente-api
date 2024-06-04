package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers;

public class KafkaConsumerResolver {

    public String getPagamentoConsumer() {
        return "pagamento";
    }

    public String getClienteConsumer() {
        return "cliente";
    }

    public String getCumposConsumer() {
        return "cupom";
    }
}
