package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.exception.checkout.PedidoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.domain.gateway.pedido.PedidoInterface;
import com.fiap.msclienteapi.domain.gateway.producers.PedidoEntregueProducerInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfrimaEntregaPedidoUseCaseTests {
    
    @Mock
    PedidoInterface pedidoInterface;
    
    @Mock
    BuscaPedidoInterface buscaPedidoInterface;
    
    @Mock
    PedidoEntregueProducerInterface pedidoEntregueProducerInterface;

    @InjectMocks
    ConfrimaEntregaPedidoUseCase confrimaEntregaPedidoUseCase;

    @Test
    public void deveRetornarOutputComMensagemDeErroCasoPedidoNaoSejaEncontrado() {
        try {
            
            confrimaEntregaPedidoUseCase.execute(UUID.randomUUID(), UUID.randomUUID());
            confrimaEntregaPedidoUseCase.getOutputInterface();
            OutputInterface output = confrimaEntregaPedidoUseCase.getOutputInterface();

            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido não encontrado");
        } catch (Exception e) {}

    }

    @Test
    public void deveRetornarUmOutputDeSucessoCasoPedidoSejaEncontrado() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();

        Pedido pedido = new Pedido(clienteUUID);
        pedido.setUuid(pedidoUUID);
        try {
            when(buscaPedidoInterface.encontraPedidoShortPorUuid(pedidoUUID, clienteUUID)).thenReturn(pedido);
            
            confrimaEntregaPedidoUseCase.execute(pedidoUUID, clienteUUID);
            confrimaEntregaPedidoUseCase.getOutputInterface();
            OutputInterface output = confrimaEntregaPedidoUseCase.getOutputInterface();

            assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Ok");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido entregue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deveRetornarUmOutputComErroCasoOcorraAlgumaExcecao() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();

        Pedido pedido = new Pedido(clienteUUID);
        pedido.setUuid(pedidoUUID);
        try {
            when(buscaPedidoInterface.encontraPedidoShortPorUuid(pedidoUUID, clienteUUID)).thenThrow(PedidoNaoEncontradoException.class);
            
            confrimaEntregaPedidoUseCase.execute(pedidoUUID, clienteUUID);
            confrimaEntregaPedidoUseCase.getOutputInterface();
            OutputInterface output = confrimaEntregaPedidoUseCase.getOutputInterface();

            assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}