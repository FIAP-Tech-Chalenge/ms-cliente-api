package com.fiap.msclienteapi.domain.entity.pedido;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class ClienteTests {
    
    @Test
    public void deveInstanciarUmCliente() {
        Cliente cliente = new Cliente("cliente", "000", "cliente@email.com", UUID.randomUUID());
        assertInstanceOf(Cliente.class, cliente);
    }
}
