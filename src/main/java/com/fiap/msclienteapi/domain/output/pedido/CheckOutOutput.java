package com.fiap.msclienteapi.domain.output.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import lombok.Getter;

@Getter
public class CheckOutOutput implements OutputInterface {
    private final Checkout checkout;
    private final OutputStatus outputStatus;

    public CheckOutOutput(Checkout checkout, OutputStatus outputStatus) {
        this.checkout = checkout;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.checkout;
    }
}
