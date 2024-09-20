package com.fiap.msclienteapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.consumers.KafkaConsumerResolver;
import com.fiap.msclienteapi.infra.model.ProdutoModel;
import com.fiap.msclienteapi.infra.repository.ProdutoRepository;
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
public class ProdutoConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final ProdutoRepository produtoRepository;
    private final Logger logger = LoggerFactory.getLogger(ProdutoConsumer.class);

    public ProdutoConsumer(Properties kafkaConsumerProperties,
                           ProdutoRepository produtoRepository) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getProdutoConsumer()));
        this.objectMapper = new ObjectMapper();
        this.produtoRepository = produtoRepository;
    }

    public void runConsumer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Mensagem recebida - Tópico: {}, Chave: {}, Valor: {}", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String uuid = messageJson.get("produto_uuid").asText();
                        double valor = messageJson.get("produto_valor").asDouble();
                        String nome = messageJson.get("produto_nome").asText();
                        String descricao = messageJson.get("produto_descricao").asText();
                        String categoria = messageJson.get("produto_categoria").asText();
                        int quantidade = messageJson.get("produto_quantidade").asInt();

                        ProdutoModel produtoModel = new ProdutoModel();
                        produtoModel.setUuid(UUID.fromString(uuid));
                        produtoModel.setValor((float) valor);
                        produtoModel.setNome(nome);
                        produtoModel.setDescricao(descricao);
                        produtoModel.setCategoria(CategoriaEnum.valueOf(categoria));
                        produtoModel.setQuantidade(quantidade);
                        produtoRepository.save(produtoModel);
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
