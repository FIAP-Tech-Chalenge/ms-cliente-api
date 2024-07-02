package com.fiap.msclienteapi.domain.presenters.cliente.identifica;


import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.output.cliente.IdentificaClienteOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class IdentificaClientePresenterTests {
    
    @Mock
    IdentificaClienteOutput identificaClienteOutput;
    
    IdentificaClientePresenter identificaClientePresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        identificaClientePresenter = new IdentificaClientePresenter(identificaClienteOutput);
    }

    @Test
    public void deveObterUmOutput() {
        UUID clienteUuid = UUID.randomUUID();
        Cliente cliente = new Cliente("cliente", "000", "cliente@email.com", clienteUuid);
        when(identificaClienteOutput.getCliente()).thenReturn(cliente);

        var clienteRetornado = identificaClientePresenter.getOutput().getCliente();
        assertThat(clienteRetornado.getNome()).isEqualTo("cliente");
        assertThat(clienteRetornado.getCpf()).isEqualTo("000");
        assertThat(clienteRetornado.getEmail()).isEqualTo("cliente@email.com");
        assertThat(clienteRetornado.getUuid()).isEqualTo(clienteUuid);
    }

    @Test
    void deveRetornarUmArrayDeCliente() {
        UUID clienteUuid = UUID.randomUUID();
        Cliente cliente = new Cliente("cliente", "000", "cliente@email.com", clienteUuid);
        
        var clienteArray = new HashMap<>();
        clienteArray.put("nome", cliente.getNome());
        clienteArray.put("cpf", cliente.getCpf());
        clienteArray.put("email", cliente.getEmail());
        clienteArray.put("uuid", cliente.getUuid().toString());

        when(identificaClienteOutput.getCliente()).thenReturn(cliente);

        var clienteRetornado = identificaClientePresenter.toArray();
        assertThat(clienteRetornado).isEqualTo(clienteArray);
    }
}
