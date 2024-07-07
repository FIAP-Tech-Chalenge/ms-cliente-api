package com.fiap.msclienteapi;

import com.fiap.msclienteapi.infra.queue.kafka.consumers.EntregaConsumer;
import com.fiap.msclienteapi.infra.queue.kafka.consumers.ProdutoConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsClienteApiApplication {

	private final ProdutoConsumer produtoConsumer;

	private final EntregaConsumer entregaConsumer;

	@Autowired
	public MsClienteApiApplication(ProdutoConsumer produtoConsumer, EntregaConsumer entregaConsumer) {
		this.produtoConsumer = produtoConsumer;
		this.entregaConsumer = entregaConsumer;
	}

	public static void main(String[] args) {
		SpringApplication.run(MsClienteApiApplication.class, args);
	}

	@PostConstruct
	public void startConsumer() {
		Thread consumerThread = new Thread(produtoConsumer::runConsumer);
		Thread consumerThread2 = new Thread(entregaConsumer::runConsumer);
		consumerThread.start();
		consumerThread2.start();
	}

}
