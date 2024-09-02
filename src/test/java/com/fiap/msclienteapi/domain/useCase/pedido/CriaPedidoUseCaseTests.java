package com.fiap.msclienteapi.domain.useCase.pedido;


import com.fiap.msclienteapi.domain.entity.pedido.Cliente;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.domain.exception.pedido.ClienteNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.cliente.ClienteInterface;
import com.fiap.msclienteapi.domain.gateway.pedido.PedidoInterface;
import com.fiap.msclienteapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.input.pedido.CriarPedidoInput;
import com.fiap.msclienteapi.domain.input.pedido.ProdutoPedidoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CriaPedidoUseCaseTests {

    @Mock
    PedidoInterface pedidoInterface;

    @Mock
    ClienteInterface clienteInterface;

    @Mock
    BuscaProdutoInterface produtoInterface;
    
    @InjectMocks
    CriaPedidoUseCase criaPedidoUseCase;

    @Test
    public void deveRetornarOutputDeSucesso() {
        UUID clienteUuid = UUID.randomUUID();
        UUID pedidoUuid = UUID.randomUUID();
        Cliente cliente = new Cliente("cliente", "86157833068", "cliente@cliente.com", clienteUuid);
        Pedido pedido = new Pedido(pedidoUuid, clienteUuid, StatusPedido.RECEBIDO, StatusPagamento.NAO_PAGO, 20, 10.0f);
        pedido.setProdutos(new ArrayList<>());
        List<ProdutoPedidoInput> pedidoInput = new ArrayList<>();
        pedidoInput.add(new ProdutoPedidoInput(clienteUuid, 1, CategoriaEnum.LANCHE));
        CriarPedidoInput input = new CriarPedidoInput(null, pedidoInput, null);
        Produto produto = new Produto(null, null, null, null, null);

        try {
            when(pedidoInterface.criaPedido(any(Pedido.class))).thenReturn(pedido);
            when(clienteInterface.buscaClientePorUuid(input.clienteUuid())).thenReturn(cliente);
            when(produtoInterface.encontraProdutoPorUuid(input.produtoList().get(0).uuid())).thenReturn(produto);

            criaPedidoUseCase.execute(input);
            OutputInterface output = criaPedidoUseCase.getCriaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(201);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Created");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido criado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deveRetornarOutputFalhaAoReceberUmPedidoSemProdutos() {
        UUID clienteUuid = UUID.randomUUID();
        Cliente cliente = new Cliente("cliente", "86157833068", "cliente@cliente.com", clienteUuid);
        List<ProdutoPedidoInput> produtoList = new ArrayList<>();
        CriarPedidoInput input = new CriarPedidoInput(clienteUuid, produtoList, null);

        try {
            when(clienteInterface.buscaClientePorUuid(input.clienteUuid())).thenReturn(cliente);

            criaPedidoUseCase.execute(input);
            OutputInterface output = criaPedidoUseCase.getCriaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(400);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Bad Request");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido vazio");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deveRetornarOutputFalhaAoReceberUmPedidoComProdutoSemEstoque() {
        UUID clienteUuid = UUID.randomUUID();
        UUID pedidoUuid = UUID.randomUUID();
        Cliente cliente = new Cliente("cliente", "86157833068", "cliente@cliente.com", clienteUuid);

        Pedido pedido = new Pedido(pedidoUuid, clienteUuid, StatusPedido.RECEBIDO, StatusPagamento.NAO_PAGO, 20, 10.0f);
        pedido.setProdutos(new ArrayList<>());
        
        List<ProdutoPedidoInput> produtoList = new ArrayList<>();
        produtoList.add(new ProdutoPedidoInput(clienteUuid, 0, CategoriaEnum.LANCHE));
        CriarPedidoInput input = new CriarPedidoInput(null, produtoList, null);

        Produto produto = new Produto("hamburguer", 10.0f, "ham", CategoriaEnum.LANCHE, 0);

        try {
            when(clienteInterface.buscaClientePorUuid(input.clienteUuid())).thenReturn(cliente);
            when(produtoInterface.encontraProdutoPorUuid(input.produtoList().get(0).uuid())).thenReturn(produto);

            criaPedidoUseCase.execute(input);
            OutputInterface output = criaPedidoUseCase.getCriaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(422);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Unprocessable Entity");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Produto com quantidade inválida");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deveRetornarOutputFalhaAoReceberUmPedidoCasoClienteDoPedidoNaoSejaEncontrado() {
        CriarPedidoInput input = new CriarPedidoInput(null, new ArrayList<>(), null);
        try {
            when(clienteInterface.buscaClientePorUuid(input.clienteUuid())).thenThrow(ClienteNaoEncontradoException.class);

            criaPedidoUseCase.execute(input);
            OutputInterface output = criaPedidoUseCase.getCriaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not Found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deveRetornarOutputFalhaAoReceberUmErroInterno() {
        CriarPedidoInput input = new CriarPedidoInput(null, new ArrayList<>(), null);
        try {
            when(clienteInterface.buscaClientePorUuid(input.clienteUuid())).thenThrow(RuntimeException.class);

            criaPedidoUseCase.execute(input);
            OutputInterface output = criaPedidoUseCase.getCriaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(500);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Internal Error");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}