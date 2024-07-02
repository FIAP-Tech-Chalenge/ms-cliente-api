package com.fiap.msclienteapi.domain.output.pedido;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;

public class CriaPedidoOutputTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        Pedido pedido = new Pedido(UUID.randomUUID());
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        CriaPedidoOutput checkOutOutput = new CriaPedidoOutput(pedido, outputStatus);

        assertThat(checkOutOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(checkOutOutput.getBody()).isInstanceOf(Pedido.class);
    }
}
