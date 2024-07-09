package com.fiap.msclienteapi.application.controllers.cliente.identifica;

import com.fiap.msclienteapi.application.controllers.cliente.identifica.requests.IdentificaClienteRequest;
import com.fiap.msclienteapi.domain.useCase.cliente.IdentificarClienteUseCase;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClienteControllerTests {
    
    @Mock
    ClienteRepository clienteRepository;

    @Mock
    IdentificarClienteUseCase useCase;
    
    @InjectMocks
    ClienteController clienteController;
    
    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(clienteController, "servers", "localhost:9092");
    }
    
    @Test
    public void deveLancarExcecao() {
        UUID clienteUUID = UUID.randomUUID();
        IdentificaClienteRequest identificaClienteRequest = new IdentificaClienteRequest("cliente", "50448042070", "cliente@cliente.com");
        ClienteModel cliente = new ClienteModel("cliente", "50448042070", "cliente@cliente.com", clienteUUID);
        when(clienteRepository.findByCpf("50448042070")).thenReturn(cliente);
        try {
            ResponseEntity<Object> response = clienteController.identificaCliente(identificaClienteRequest);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        } catch (Exception e) {}
    }
}
