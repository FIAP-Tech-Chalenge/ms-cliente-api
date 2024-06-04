package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers;

import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.KafkaTopicsEnum;

public class KafkaConsumerResolver {

    public String getPagamentoConsumer() {
        return KafkaTopicsEnum.pagamento.name();

    }

    public String getClienteConsumer() {
        return KafkaTopicsEnum.cliente.name();
    }
}
