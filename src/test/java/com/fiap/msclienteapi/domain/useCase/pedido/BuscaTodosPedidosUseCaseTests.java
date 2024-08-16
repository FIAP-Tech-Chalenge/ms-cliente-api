package com.fiap.msclienteapi.domain.useCase.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
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
public class BuscaTodosPedidosUseCaseTests {

    @Mock
    BuscaPedidoInterface buscaPedidoInterface;

    @InjectMocks
    BuscaTodosPedidosUseCase buscaTodosPedidosUseCase;

    @Test
    public void deveRetornarOutputDeSucessoComTodosOsPedidos() {
        List<Pedido> pedidos = new ArrayList<>();

        when(buscaPedidoInterface.findAll()).thenReturn(pedidos);
        
        buscaTodosPedidosUseCase.execute();
        OutputInterface output = buscaTodosPedidosUseCase.getBuscaProdutoOutput();

        assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
        assertThat(output.getOutputStatus().getMessage()).isEqualTo("Lista de pedidos");
    }

    @Test
    public void deveRetornarOutputDeErroAoReceberException() {
        when(buscaPedidoInterface.findAll()).thenThrow(RuntimeException.class);
        
        buscaTodosPedidosUseCase.execute();
        OutputInterface output = buscaTodosPedidosUseCase.getBuscaProdutoOutput();

        assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage()).isEqualTo("Erro no servidor");
    }
}