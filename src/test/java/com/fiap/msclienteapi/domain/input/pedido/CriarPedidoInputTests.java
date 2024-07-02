package com.fiap.msclienteapi.domain.input.pedido;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriarPedidoInputTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        UUID clienteUUID = UUID.randomUUID();
        List<ProdutoPedidoInput> listaProdutoPedido = new ArrayList<>();
        CriarPedidoInput criarPedidoInput = new CriarPedidoInput(clienteUUID, listaProdutoPedido, 1L);
        
        assertThat(criarPedidoInput.clienteUuid()).isEqualTo(clienteUUID);
        assertTrue(criarPedidoInput.produtoList().isEmpty());
        assertEquals(1L, criarPedidoInput.numeroPedido());
    }
}
