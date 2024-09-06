package com.fiap.msclienteapi;

import com.fiap.msclienteapi.infra.queue.kafka.consumers.EntregaConsumer;
import com.fiap.msclienteapi.infra.queue.kafka.consumers.PedidoEmPreparoConsumer;
import com.fiap.msclienteapi.infra.queue.kafka.consumers.ProdutoConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsClienteApiApplication {

	private final ProdutoConsumer produtoConsumer;

	private final EntregaConsumer entregaConsumer;
	
	private final PedidoEmPreparoConsumer pedidoEmPreparoConsumer;

	@Autowired
	public MsClienteApiApplication(ProdutoConsumer produtoConsumer, EntregaConsumer entregaConsumer, PedidoEmPreparoConsumer pedidoEmPreparoConsumer) {
		this.produtoConsumer = produtoConsumer;
		this.entregaConsumer = entregaConsumer;
		this.pedidoEmPreparoConsumer = pedidoEmPreparoConsumer;
	}
	public static void main(String[] args) {
		SpringApplication.run(MsClienteApiApplication.class, args);
	}

	@PostConstruct
	public void startConsumer() {
		Thread consumerThread = new Thread(produtoConsumer::runConsumer);
		Thread consumerThread2 = new Thread(entregaConsumer::runConsumer);
		Thread consumerThread3 = new Thread(pedidoEmPreparoConsumer::runConsumer);
		consumerThread.start();
		consumerThread2.start();
		consumerThread3.start();
	}

}
