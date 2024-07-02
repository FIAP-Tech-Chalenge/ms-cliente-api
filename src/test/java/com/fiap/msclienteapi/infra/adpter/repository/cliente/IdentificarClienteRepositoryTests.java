package com.fiap.msclienteapi.infra.adpter.repository.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IdentificarClienteRepositoryTests {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    IdentificarClienteRepository identificarClienteRepository;
    
    @Test
    public void deveRetornarUmClientePorCpf(){
        ClienteModel clienteModel = new ClienteModel("cliente", "198237124", "cliente@cliente.com", UUID.randomUUID());
        when(clienteRepository.findByCpf(anyString())).thenReturn(clienteModel);
        Cliente clienteRetornado = identificarClienteRepository.buscaClientePorCpf(clienteModel.getCpf());
        assertThat(clienteRetornado.getNome()).isEqualTo(clienteModel.getNome());
        assertThat(clienteRetornado.getCpf()).isEqualTo(clienteModel.getCpf());
        assertThat(clienteRetornado.getEmail()).isEqualTo(clienteModel.getEmail());
    }

    @Test
    public void deveRetornarNULLCasoCPFInformadoNaoPossuaRelacao(){
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        Cliente clienteRetornado = identificarClienteRepository.buscaClientePorCpf(anyString());
        assertThat(clienteRetornado).isNull();
    }

    @Test
    public void deveIdentificarUmCliente(){
        Cliente cliente = new Cliente("cliente", "198237124", "cliente@cliente.com", UUID.randomUUID());
        ClienteModel clienteModel = new ClienteModel("cliente", "198237124", "cliente@cliente.com", UUID.randomUUID());
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);
        Cliente clienteRetornado = identificarClienteRepository.identificarCliente(cliente);
        assertThat(clienteRetornado.getNome()).isEqualTo(cliente.getNome());
        assertThat(clienteRetornado.getCpf()).isEqualTo(cliente.getCpf());
        assertThat(clienteRetornado.getEmail()).isEqualTo(cliente.getEmail());

    }
}