package com.fiap.msclienteapi;

import com.fiap.msclienteapi.infra.queue.kafka.consumers.ProdutoConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsClienteApiApplication {

	private final ProdutoConsumer consumer;

	@Autowired
	public MsClienteApiApplication(ProdutoConsumer consumer) {
		this.consumer = consumer;
	}

	public static void main(String[] args) {
		SpringApplication.run(MsClienteApiApplication.class, args);
	}

	@PostConstruct
	public void startConsumer() {
		Thread consumerThread = new Thread(consumer::runConsumer);
		consumerThread.start();
	}

}
