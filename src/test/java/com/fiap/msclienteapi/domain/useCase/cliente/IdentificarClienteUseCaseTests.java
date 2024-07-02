package com.fiap.msclienteapi.domain.useCase.cliente;


import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.gateway.cliente.IdentificarClienteInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.input.cliente.IdentificaClienteInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IdentificarClienteUseCaseTests {

	@Mock
	IdentificarClienteInterface identificaCliente;

	@Mock 
	OutputInterface outputInterface;

	@InjectMocks
	IdentificarClienteUseCase identificarClienteUseCase;

	@Test
	public void deveRetornarOutputDeSucessoAoEncontrarCliente() {
		UUID clienteUUID = UUID.randomUUID();
		IdentificaClienteInput input = new IdentificaClienteInput("cliente", "0000", "cliente@email.com");
		Cliente cliente = new Cliente(input.getNome(), input.getCpf(), input.getEmail(), clienteUUID);

		when(identificaCliente.buscaClientePorCpf(anyString())).thenReturn(cliente);

		try {
			identificarClienteUseCase.execute(input);
			OutputInterface output = identificarClienteUseCase.getIdentificaClienteOutput();
			assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
			assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Ok");
			assertThat(output.getOutputStatus().getMessage()).isEqualTo("Cliente encontrado");
		} catch (Exception e) {}
	}

	@Test
	public void deveRetornarUmOutputComErroCasoClienteComCPFinvalido() {
		UUID clienteUUID = UUID.randomUUID();
		IdentificaClienteInput input = new IdentificaClienteInput("cliente", "0000", "cliente@email.com");
		Cliente cliente = new Cliente(input.getNome(), input.getCpf(), input.getEmail(), clienteUUID);

		when(identificaCliente.buscaClientePorCpf(anyString())).thenReturn(null);

		try {
			identificaCliente.identificarCliente(cliente);
			identificarClienteUseCase.execute(input);
			OutputInterface output = identificarClienteUseCase.getIdentificaClienteOutput();
			assertThat(output.getOutputStatus().getCode()).isEqualTo(422);
			assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Unprocessable Entity");
			assertThat(output.getOutputStatus().getMessage()).isEqualTo("CPF inválido");
		} catch (Exception e) {}
	}

	@Test
	public void deveRetornarUmOutputComErroCasoClienteComNomeNulo() {
		UUID clienteUUID = UUID.randomUUID();
		IdentificaClienteInput input = new IdentificaClienteInput("", "00433732083", "cliente@email.com");
		Cliente cliente = new Cliente(input.getNome(), input.getCpf(), input.getEmail(), clienteUUID);

		when(identificaCliente.buscaClientePorCpf(anyString())).thenReturn(null);

		try {
			identificaCliente.identificarCliente(cliente);
			identificarClienteUseCase.execute(input);
			OutputInterface output = identificarClienteUseCase.getIdentificaClienteOutput();
			assertThat(output.getOutputStatus().getCode()).isEqualTo(422);
			assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Unprocessable Entity");
			assertThat(output.getOutputStatus().getMessage()).isEqualTo("Nome é obrigatório");
		} catch (Exception e) {}
	}

	@Test
	public void deveRetornarUmOutputComErroCasoClienteEmailVazio() {
		UUID clienteUUID = UUID.randomUUID();
		IdentificaClienteInput input = new IdentificaClienteInput("cliente", "00433732083", "");
		Cliente cliente = new Cliente(input.getNome(), input.getCpf(), input.getEmail(), clienteUUID);

		when(identificaCliente.buscaClientePorCpf(anyString())).thenReturn(null);

		try {
			identificaCliente.identificarCliente(cliente);
			identificarClienteUseCase.execute(input);
			OutputInterface output = identificarClienteUseCase.getIdentificaClienteOutput();
			assertThat(output.getOutputStatus().getCode()).isEqualTo(422);
			assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Unprocessable Entity");
			assertThat(output.getOutputStatus().getMessage()).isEqualTo("Email é obrigatório");
		} catch (Exception e) {}
	}

	@Test
	public void deveRetornarOutputComUmClienteRecemCriado() {
		IdentificaClienteInput input = new IdentificaClienteInput("cliente", "00433732083", "cliente@email.com");

		when(identificaCliente.buscaClientePorCpf(anyString())).thenReturn(null);

		try {
			identificarClienteUseCase.execute(input);
			OutputInterface output = identificarClienteUseCase.getIdentificaClienteOutput();
			assertThat(output.getOutputStatus().getCode()).isEqualTo(201);
			assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Created");
			assertThat(output.getOutputStatus().getMessage()).isEqualTo("Cliente criado");
		} catch (Exception e) {}
	}

	@Test
	public void deveRetornarOutputComErroAoFalharNaBusca() {
		IdentificaClienteInput input = new IdentificaClienteInput("cliente", "00433732083", "cliente@cliente.com");
		
		when(identificaCliente.buscaClientePorCpf(anyString())).thenThrow(RuntimeException.class);

		try {
			identificarClienteUseCase.execute(input);
			OutputInterface output = identificarClienteUseCase.getIdentificaClienteOutput();
			assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
			assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Error");
		} catch (Exception e) {}
	}

}