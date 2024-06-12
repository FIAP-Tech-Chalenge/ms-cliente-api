package com.fiap.msclienteapi.domain.output.pedido;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;

public class BuscaTodosPedidoOutputTests {

    @Test
    public void deveInstanciarClasseCorretamente() {
        List<Pedido> listaProdutoPedido = new ArrayList<>();
        OutputStatus outputStatus = new OutputStatus(0, null, null);
        BuscaTodosPedidoOutput buscaPedidoOutput = new BuscaTodosPedidoOutput(listaProdutoPedido, outputStatus);

        assertThat(buscaPedidoOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(buscaPedidoOutput.getBody()).isNotNull();
    }
}
