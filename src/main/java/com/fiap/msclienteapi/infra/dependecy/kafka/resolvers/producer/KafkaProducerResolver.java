package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer;

import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.KafkaTopicsEnum;

public class KafkaProducerResolver {

    public String getPagamentoProducer() {
        return KafkaTopicsEnum.pagamento.name();
    }

    public String getClienteProducer() {
        return KafkaTopicsEnum.cliente.name();
    }

    public String getClienteInativoProducer() {
        return KafkaTopicsEnum.clienteInativo.name();
    }

    public String getPedidoProducer() {
        return KafkaTopicsEnum.pedido.name();
    }
    public String getPedidoEntregeProducer() {
        return KafkaTopicsEnum.entrega.name();
    }
}
