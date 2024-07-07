package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers;

import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.KafkaTopicsEnum;

public class KafkaConsumerResolver {

    public String getProdutoConsumer() {
        return KafkaTopicsEnum.produto.name();
    }

    public String getEntregaConsumer() {
        return KafkaTopicsEnum.entrega.name();
    }
}
