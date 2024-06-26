package com.fiap.msclienteapi.domain.output.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
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
public class IdentificaClienteOutput implements OutputInterface {
    private Cliente cliente;
    private OutputStatus outputStatus;

    public IdentificaClienteOutput(Cliente clienteEntity, OutputStatus outputStatus) {
        this.cliente = clienteEntity;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.cliente;
    }
}
