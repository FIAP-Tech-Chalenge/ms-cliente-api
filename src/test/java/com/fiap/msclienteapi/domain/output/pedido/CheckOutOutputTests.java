package com.fiap.msclienteapi.domain.output.pedido;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;

public class CheckOutOutputTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        Checkout checkout = new Checkout(UUID.randomUUID(), StatusPagamento.PAGO);
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        CheckOutOutput checkOutOutput = new CheckOutOutput(checkout, outputStatus);

        assertThat(checkOutOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(checkOutOutput.getBody()).isInstanceOf(Checkout.class);
    }
}
