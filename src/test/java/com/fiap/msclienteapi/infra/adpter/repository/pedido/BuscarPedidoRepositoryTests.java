package com.fiap.msclienteapi.infra.adpter.repository.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.model.PedidoProdutoModel;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BuscarPedidoRepositoryTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;
    
    @InjectMocks
    BuscarPedidoRepository buscarPedidoRepository;
    
    @Test
    public void deveRetornarTodosOsPedidos(){
        List<PedidoModel> pedidosModels = new ArrayList<>();
        pedidosModels.add(new PedidoModel(UUID.randomUUID(), null, UUID.randomUUID(), null, StatusPedido.EM_PREPARACAO, StatusPagamento.NAO_PAGO, 20, 10.0F));
        List<PedidoProdutoModel> pedidoProdutoModels = new ArrayList<>();
        pedidoProdutoModels.add(new PedidoProdutoModel(1l, 10.0F, 1 , CategoriaEnum.ACOMPANHAMENTO, UUID.randomUUID(), UUID.randomUUID()));
        when(pedidoRepository.findAll()).thenReturn(pedidosModels);
        when(pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedidosModels.get(0).getUuid())).thenReturn(pedidoProdutoModels);
        List<Pedido> pedidoRetorno = buscarPedidoRepository.findAll();
        assertThat(pedidoRetorno).isNotNull();
    }


    @Test
    public void deveRetornarUmPedidoViaUUID(){
        UUID pedidoModelUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        PedidoModel pedidoModel = new PedidoModel(pedidoModelUUID, 10l, clienteUUID, null, StatusPedido.EM_PREPARACAO, StatusPagamento.NAO_PAGO, 20, 10.0f);
        List<PedidoProdutoModel> pedidoProdutoModels = new ArrayList<>();
        pedidoProdutoModels.add(new PedidoProdutoModel(1l, 10.0F, 1 , CategoriaEnum.ACOMPANHAMENTO, UUID.randomUUID(), UUID.randomUUID()));
        
        when(pedidoRepository.findByUuid(pedidoModelUUID)).thenReturn(pedidoModel);
        when(pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedidoModel.getUuid())).thenReturn(pedidoProdutoModels);
        Pedido pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(pedidoModelUUID, clienteUUID);

        assertThat(pedidoEncontrado).isNotNull();
        assertThat(pedidoEncontrado.getUuid()).isEqualTo(pedidoModel.getUuid());
        assertThat(pedidoEncontrado.getProdutos()).isNotEmpty();
    }

    @Test
    public void deveRetornarNullCasoBuscaPorUUIDNaoRetorneResultados(){
        UUID pedidoModelUUID = UUID.randomUUID();

        when(pedidoRepository.findByUuid(pedidoModelUUID)).thenReturn(null);
        Pedido pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(pedidoModelUUID, UUID.randomUUID());

        assertThat(pedidoEncontrado).isNull();
    }

    @Test
    public void deveRetornarNullCasoPedidoEncontradoNaoCorrespondaAoPedidoDoCliente(){
        UUID pedidoModelUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        PedidoModel pedidoModel = new PedidoModel(pedidoModelUUID, 10l, clienteUUID, null, StatusPedido.EM_PREPARACAO, StatusPagamento.NAO_PAGO, 20, 10.0f);
        
        when(pedidoRepository.findByUuid(pedidoModelUUID)).thenReturn(pedidoModel);
        Pedido pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(pedidoModelUUID, UUID.randomUUID());

        assertThat(pedidoEncontrado).isNull();
    }

    @Test
    public void deveRetornarNuloCasoPedidoNaoEncontrado() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        
        when(pedidoRepository.findByUuid(pedidoUUID)).thenReturn(null);

        Pedido valorRetornado = buscarPedidoRepository.encontraPedidoShortPorUuid(pedidoUUID, clienteUUID);
        assertThat(valorRetornado).isNull();
    }
    @Test
    public void deveRetornarNuloCasoClienteUsadoNaBuscaSejaDiferenteDoClienteEncontradoNoPedido() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        PedidoModel pedido = new PedidoModel();
        pedido.setClienteId(UUID.randomUUID());
        
        when(pedidoRepository.findByUuid(pedidoUUID)).thenReturn(pedido);

        Pedido valoreRetornado = buscarPedidoRepository.encontraPedidoShortPorUuid(pedidoUUID, clienteUUID);
        assertThat(valoreRetornado).isNull();
    }

    @Test
    public void deveAtulizarOStatusDoPedidoParaFINALIZADO() {
        UUID pedidoUUID = UUID.randomUUID();
        UUID clienteUUID = UUID.randomUUID();
        PedidoModel pedido = new PedidoModel();
        pedido.setUuid(pedidoUUID);
        pedido.setClienteId(clienteUUID);
        pedido.setStatusPedido(StatusPedido.RECEBIDO);
        
        when(pedidoRepository.findByUuid(pedidoUUID)).thenReturn(pedido);

        Pedido valoreRetornado = buscarPedidoRepository.encontraPedidoShortPorUuid(pedidoUUID, clienteUUID);
        assertThat(valoreRetornado.getStatusPedido()).isEqualTo(StatusPedido.FINALIZADO);
    }

}