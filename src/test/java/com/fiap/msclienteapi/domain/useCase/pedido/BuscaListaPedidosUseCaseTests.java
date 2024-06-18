package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaListaPedidoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscaListaPedidosUseCaseTests {

    @Mock
    BuscaListaPedidoInterface buscaListaPedidoInterface;

    @InjectMocks
    BuscaListaPedidosUseCase buscaListaPedidosUseCase;

    @Test
    public void deveRetornarOutputComSucesso() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(null));

        when(buscaListaPedidoInterface.findListaPedidos()).thenReturn(pedidos);
        buscaListaPedidosUseCase.execute();
        OutputInterface output = buscaListaPedidosUseCase.getBuscaProdutoOutput();

        assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
        assertThat(output.getOutputStatus().getMessage()).isEqualTo("Lista de pedidos");
    }

    @Test
    public void deveRetornarOutputComFalha() {

        when(buscaListaPedidoInterface.findListaPedidos()).thenThrow(RuntimeException.class);
        buscaListaPedidosUseCase.execute();
        OutputInterface output = buscaListaPedidosUseCase.getBuscaProdutoOutput();

        assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Server Error");
    }

}