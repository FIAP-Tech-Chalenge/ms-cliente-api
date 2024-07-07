package com.fiap.msclienteapi.application.controllers.cliente.pagamento;

import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessarPagamentoControllerTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @InjectMocks
    ProcessarPagamentoController processarPagamentoController;

    // @Test
    // public void deveRetornarOutputError(){
    //     processarPagamentoController.processarCheckout(UUID.randomUUID());
    // }
    
}
