package com.fiap.msclienteapi.domain.presenters.cliente.identifica;

import com.fiap.msclienteapi.domain.generic.presenter.PresenterInterface;
import com.fiap.msclienteapi.domain.output.cliente.InativacaoClienteOutput;

import java.util.HashMap;
import java.util.Map;

public class InativacaoClientePresenter implements PresenterInterface {
    private final InativacaoClienteOutput inativacaoClienteOutput;

    public InativacaoClientePresenter(InativacaoClienteOutput inativacaoClienteOutput) {
        this.inativacaoClienteOutput = inativacaoClienteOutput;
    }

    public Map<String, Object> toArray() {
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome", this.inativacaoClienteOutput.getCliente().getNome());
        return cliente;
    }

    public InativacaoClienteOutput getOutput() {
        return this.inativacaoClienteOutput;
    }
}
