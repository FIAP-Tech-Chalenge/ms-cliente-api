package com.fiap.msclienteapi.infra.adpter.repository.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.gateway.cliente.IdentificarClienteInterface;
import com.fiap.msclienteapi.infra.model.ClienteInativoModel;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import com.fiap.msclienteapi.infra.repository.ClienteInativacaoRepository;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
public class IdentificarClienteRepository implements IdentificarClienteInterface {

    private final ClienteRepository clienteRepository;
    private final ClienteInativacaoRepository clienteInativacaoRepository;

    @Override
    public Cliente buscaClientePorNome(String nome) {
        ClienteModel clienteModel = this.clienteRepository.findByNome(nome);
        if (clienteModel == null) {
            return null;
        }
        return new Cliente(clienteModel.getNome(), clienteModel.getCpf(), clienteModel.getEmail(), clienteModel.getUuid());
    }

    @Override
    public Cliente inativarCliente(Cliente cliente) {
        ClienteInativoModel clienteInativoModel = new ClienteInativoModel(cliente.getUuid());
        this.clienteInativacaoRepository.save(clienteInativoModel);

        return cliente;
    }

    public Cliente buscaClientePorCpf(String cpf) {
        ClienteModel clienteModel = this.clienteRepository.findByCpf(cpf);
        if (clienteModel == null) {
            return null;
        }
        return new Cliente(clienteModel.getNome(), clienteModel.getCpf(), clienteModel.getEmail(), clienteModel.getUuid());
    }

    @Override
    public Cliente identificarCliente(Cliente cliente) {
        ClienteModel clienteModel =this.clienteRepository.save(
            new ClienteModel(cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getUuid())
        );
        cliente.setUuid(clienteModel.getUuid());
        return cliente;
    }
}
