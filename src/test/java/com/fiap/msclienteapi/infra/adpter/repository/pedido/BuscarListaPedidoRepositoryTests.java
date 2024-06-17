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
public class BuscarListaPedidoRepositoryTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @InjectMocks
    BuscarListaPedidoRepository buscarListaPedidoRepository;
    
    @Test
    public void deveRetornarAListaDePedidos(){
        List<PedidoModel> pedidosModels = new ArrayList<>();
        pedidosModels.add(new PedidoModel(UUID.randomUUID(), null, UUID.randomUUID(), null, StatusPedido.EM_PREPARACAO, StatusPagamento.NAO_PAGO, 10.0F));
        List<PedidoProdutoModel> pedidoProdutoModels = new ArrayList<>();
        pedidoProdutoModels.add(new PedidoProdutoModel(1l, 10.0F, 1 , CategoriaEnum.ACOMPANHAMENTO, UUID.randomUUID(), UUID.randomUUID()));
        when(pedidoRepository.findListaPedido()).thenReturn(pedidosModels);
        when(pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedidosModels.get(0).getUuid())).thenReturn(pedidoProdutoModels);
        List<Pedido> pedidoRetorno = buscarListaPedidoRepository.findListaPedidos();
        assertThat(pedidoRetorno).isNotNull();
    }
}