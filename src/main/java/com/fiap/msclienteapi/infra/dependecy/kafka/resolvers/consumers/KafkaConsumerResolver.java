package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers;

import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.KafkaTopicsEnum;

public class KafkaConsumerResolver {

    public String getProdutoConsumer() {
        return KafkaTopicsEnum.produto.name();
    }

    public String getEntregaConsumer() {
        return KafkaTopicsEnum.entrega.name();
    }

    public String getPedidoConsumer() {
        return KafkaTopicsEnum.pedido.name();
    }

    public String getTempoDeEsperaConsumer() {
        return KafkaTopicsEnum.tempo_de_espera.name();
    }
}
