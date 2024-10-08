package com.fiap.msclienteapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers.KafkaConsumerResolver;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@Component
public class PedidoEmPreparoConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final PedidoRepository pedidoRepository;
    private final Logger logger = LoggerFactory.getLogger(PedidoEmPreparoConsumer.class);
    
    public PedidoEmPreparoConsumer(Properties kafkaConsumerProperties,
                           PedidoRepository pedidoRepository) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getTempoDeEsperaConsumer()));
        this.objectMapper = new ObjectMapper();
        this.pedidoRepository = pedidoRepository;
    }

        public void runConsumer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Mensagem recebida - Tópico: {}, Chave: {}, Valor: {}", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String uuid = messageJson.get("uuid_pedido").asText();
                        Long numero = messageJson.get("numero_pedido").asLong();
                        String status = messageJson.get("status_pedido").asText();
                        int tempoDePreparoEmMinutos = messageJson.get("tempo_preparo").asInt();

                        PedidoModel pedidoModel = pedidoRepository.findById(UUID.fromString(uuid)).orElse(null);

                        if (pedidoModel != null) {
                            pedidoModel.setStatusPedido(StatusPedido.valueOf(status));
                            pedidoModel.setNumeroPedido(numero);
                            pedidoModel.setTempoDePreparo(tempoDePreparoEmMinutos);

                            pedidoRepository.save(pedidoModel);
                        }else {
                            logger.error("Pedido não encontrado: {}", uuid);
                        }

                    } catch (Exception e) {
                        logger.error("Erro ao processar a mensagem: {}", e.getMessage());
                    }
                }
            }
        } finally {
            this.consumer.close();
            logger.info("Consumidor Kafka fechado.");
        }
    }

}
