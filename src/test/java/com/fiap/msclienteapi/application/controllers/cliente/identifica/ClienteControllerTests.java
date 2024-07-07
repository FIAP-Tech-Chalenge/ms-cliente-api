package com.fiap.msclienteapi.application.controllers.cliente.identifica;

import com.fiap.msclienteapi.domain.useCase.cliente.IdentificarClienteUseCase;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


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
    
    // @Test
    // public void deveLancarExcecao() {
    //     IdentificaClienteRequest identificaClienteRequest = new IdentificaClienteRequest("cliente", "50448042070", "cliente@cliente.com");
    //     try {
    //         clienteController.identificaCliente(identificaClienteRequest);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
