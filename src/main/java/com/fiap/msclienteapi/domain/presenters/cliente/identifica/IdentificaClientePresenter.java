package com.fiap.msclienteapi.domain.presenters.cliente.identifica;

import com.fiap.msclienteapi.domain.generic.presenter.PresenterInterface;
import com.fiap.msclienteapi.domain.output.cliente.IdentificaClienteOutput;

import java.util.HashMap;
import java.util.Map;

public class IdentificaClientePresenter implements PresenterInterface {
    private final IdentificaClienteOutput identificaClienteOutput;

    public IdentificaClientePresenter(IdentificaClienteOutput identificaClienteOutput) {
        this.identificaClienteOutput = identificaClienteOutput;
    }

    public Map<String, Object> toArray() {
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome", this.identificaClienteOutput.getCliente().getNome());
        cliente.put("cpf", this.identificaClienteOutput.getCliente().getCpf());
        cliente.put("email", this.identificaClienteOutput.getCliente().getEmail());
        cliente.put("uuid", this.identificaClienteOutput.getCliente().getUuid().toString());
        return cliente;
    }

    public IdentificaClienteOutput getOutput() {
        return this.identificaClienteOutput;
    }
}
