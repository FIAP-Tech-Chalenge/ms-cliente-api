package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
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
public class BuscaPedidoPorUuidUseCaseTests {

    @Mock
    BuscaPedidoInterface buscaPedido;

    @InjectMocks
    BuscaPedidoPorUuidUseCase buscaPedidoPorUuidUseCase;

    @Test
    public void deveRetornarOutputComPedido() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        Pedido pedido = new Pedido(pedidoUUID, clienteUUID, null, null, null);
        
        try {
            when(buscaPedido.encontraPedidoPorUuid(pedidoUUID, clienteUUID)).thenReturn(pedido);
            buscaPedidoPorUuidUseCase.execute((pedidoUUID), clienteUUID);
            OutputInterface output = buscaPedidoPorUuidUseCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido encontrado");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarOutputComPedidoSejaNulo() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();

        try {
            when(buscaPedido.encontraPedidoPorUuid(pedidoUUID, clienteUUID)).thenReturn(null);
            buscaPedidoPorUuidUseCase.execute((pedidoUUID), clienteUUID);
            OutputInterface output = buscaPedidoPorUuidUseCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido não encontrado");
        } catch (Exception e) {}
    }
    
    @Test
    public void deveRetornarOutputComPedidoNaoEncontradoCasoOMesmoNaoExista() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();

        try {
            when(buscaPedido.encontraPedidoPorUuid(pedidoUUID, clienteUUID)).thenThrow(PedidoNaoEncontradoException.class);
            buscaPedidoPorUuidUseCase.execute((pedidoUUID), clienteUUID);
            OutputInterface output = buscaPedidoPorUuidUseCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not Found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Produto não encontrado");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
}


    @Test
    public void deveRetornarOutputComErroCasoABuscaFalhe() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();

        try {
            when(buscaPedido.encontraPedidoPorUuid(pedidoUUID, clienteUUID)).thenThrow(RuntimeException.class);
            buscaPedidoPorUuidUseCase.execute((pedidoUUID), clienteUUID);
            OutputInterface output = buscaPedidoPorUuidUseCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Server Error");
        } catch (Exception e) {}
    }

}