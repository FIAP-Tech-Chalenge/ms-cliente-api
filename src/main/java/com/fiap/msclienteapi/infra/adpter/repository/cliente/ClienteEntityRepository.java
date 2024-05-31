package com.fiap.msclienteapi.infra.adpter.repository.cliente;

import com.fiap.msclienteapi.domain.entity.pedido.Cliente;
import com.fiap.msclienteapi.domain.exception.pedido.ClienteNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.cliente.ClienteInterface;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ClienteEntityRepository implements ClienteInterface {
    private final ClienteRepository clienteRepository;

    @Override
    public Cliente buscaClientePorUuid(UUID uuid) throws ClienteNaoEncontradoException {
        ClienteModel clienteModel = clienteRepository.findByUuid(uuid);
        if (clienteModel == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        return new Cliente(
                clienteModel.getNome(),
                clienteModel.getCpf(),
                clienteModel.getEmail(),
                clienteModel.getUuid()
        );
    }
}
