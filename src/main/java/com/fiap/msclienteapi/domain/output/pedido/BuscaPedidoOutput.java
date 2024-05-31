package com.fiap.msclienteapi.domain.output.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class BuscaPedidoOutput implements OutputInterface {

    private Pedido pedido;
    private OutputStatus outputStatus;

    public BuscaPedidoOutput(Pedido pedido, OutputStatus outputStatus) {
        this.pedido = pedido;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.pedido;
    }
}
