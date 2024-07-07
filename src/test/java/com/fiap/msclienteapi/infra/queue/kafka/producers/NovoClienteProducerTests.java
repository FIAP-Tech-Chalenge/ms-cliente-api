package com.fiap.msclienteapi.infra.queue.kafka.producers;

import com.fiap.msclienteapi.domain.generic.output.ClienteOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class NovoClienteProducerTests {

    @Test
    public void deveEnviarMensagemSemProduzirNenhumaExcecao(){
        NovoClienteProducer novoClienteProducer = new NovoClienteProducer("localhost:9092");
        ClienteOutput clienteOutput = new ClienteOutput(UUID.randomUUID(), "Cliente", "000", "cliente@cliente.com");
        novoClienteProducer.send(clienteOutput);
    }

    @Test
    public void deveCairNaExcecaoAoEnviarMensagem(){
        NovoClienteProducer novoClienteProducer = new NovoClienteProducer("localhost:9092");
        ClienteOutput clienteOutput = new ClienteOutput(null, null, null,null);
        novoClienteProducer.send(clienteOutput);
    }
}
    