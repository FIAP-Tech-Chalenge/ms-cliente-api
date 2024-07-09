package com.fiap.msclienteapi.infra.adpter.repository.cliente;

import com.fiap.msclienteapi.domain.entity.pedido.Cliente;
import com.fiap.msclienteapi.domain.exception.pedido.ClienteNaoEncontradoException;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteEntityRepositoryTests {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteEntityRepository clienteEntityRepository;

    @Test
    public void reveRetornarUmCliente() {
        UUID clientUuid = UUID.randomUUID();
        ClienteModel clienteModel = new ClienteModel("cliente", "123485960", "cliente@cliente.com", clientUuid);
        try {
            when(clienteRepository.findByUuid(clientUuid)).thenReturn(clienteModel);
            Cliente clienteRetornado = clienteEntityRepository.buscaClientePorUuid(clientUuid);
            assertThat(clienteRetornado.getNome()).isEqualTo("cliente");
            assertThat(clienteRetornado.getCpf()).isEqualTo("123485960");
            assertThat(clienteRetornado.getEmail()).isEqualTo("cliente@cliente.com");
        } catch (Exception e) {}
    }

    @Test
    public void deveGerarExcecao_QuandoBuscarClientePorUuid_ClienteNaoEncontradoException() {
        UUID clientUuid = UUID.randomUUID();
        try {
            clienteEntityRepository.buscaClientePorUuid(clientUuid);
            when(clienteRepository.findByUuid(clientUuid)).thenReturn(null);
        } catch (Exception e) {
            assertInstanceOf(ClienteNaoEncontradoException.class, e);
            assertThat(e.getMessage()).isEqualTo("Cliente não encontrado");
        }
    }

}