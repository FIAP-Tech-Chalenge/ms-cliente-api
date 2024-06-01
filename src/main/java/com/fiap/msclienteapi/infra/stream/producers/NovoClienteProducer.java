package com.fiap.msclienteapi.infra.stream.producers;

import com.fiap.msclienteapi.domain.gateway.producers.NovoClienteProducertInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;

public class NovoClienteProducer implements NovoClienteProducertInterface {
    @Override
    public void send(OutputInterface message) {
        //kafka producer
        System.out.println("Enviando mensagem para o Kafka");
        System.out.println(message.toString());
    }
}
