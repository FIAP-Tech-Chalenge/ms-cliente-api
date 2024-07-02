package com.fiap.msclienteapi.infra.adpter.repository.pedido;

import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.entity.pedido.Produto;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import com.fiap.msclienteapi.infra.model.PedidoProdutoModel;
import com.fiap.msclienteapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msclienteapi.infra.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class BuscarPedidoRepository implements BuscaPedidoInterface {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @Override
    public List<Pedido> findAll() {
        List<PedidoModel> pedidosModels = pedidoRepository.findAll();
        List<Pedido> pedidosEntities = new ArrayList<>();

        for (PedidoModel pedidoModel : pedidosModels) {
            Pedido pedidoEntity = new Pedido(
                    pedidoModel.getUuid(),
                    pedidoModel.getClienteId(),
                    pedidoModel.getStatusPedido(),
                    pedidoModel.getStatusPagamento(),
                    pedidoModel.getValorTotal()
            );

            List<PedidoProdutoModel> pedidosDoProduto = pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedidoModel.getUuid());
            List<Produto> produtosList = new ArrayList<>();
            for (PedidoProdutoModel pedidoProdutoModel : pedidosDoProduto) {
                Produto produtoEntity = new Produto(pedidoProdutoModel.getPedidoUuid(), pedidoProdutoModel.getQuantidade(), pedidoProdutoModel.getCategoria());
                produtoEntity.setValor(pedidoProdutoModel.getValor());
                produtosList.add(produtoEntity);
            }
            pedidoEntity.setProdutos(produtosList);

            pedidoEntity.setUuid(pedidoModel.getUuid());
            pedidoEntity.setNumeroPedido(pedidoModel.getNumeroPedido());
            pedidosEntities.add(pedidoEntity);
        }
        return pedidosEntities;
    }

    @Override
    public Pedido encontraPedidoPorUuid(UUID pedidoUuid, UUID clienteUuid) {
        PedidoModel pedidoModel = pedidoRepository.findByUuid(pedidoUuid);
        if (pedidoModel == null) {
            return null;
        }
        if (!pedidoModel.getClienteId().equals(clienteUuid) && clienteUuid != null) {
            return null;
        }
        List<PedidoProdutoModel> pedidosDoProduto = pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedidoModel.getUuid());
        List<Produto> produtosList = new ArrayList<>();
        for (PedidoProdutoModel pedidoProdutoModel : pedidosDoProduto) {
            Produto produtoEntity = new Produto(pedidoProdutoModel.getPedidoUuid(), pedidoProdutoModel.getQuantidade(), pedidoProdutoModel.getCategoria());
            produtoEntity.setValor(pedidoProdutoModel.getValor());
            produtosList.add(produtoEntity);
        }
        Pedido pedidoEntity = new Pedido(
                pedidoModel.getUuid(),
                pedidoModel.getClienteId(),
                pedidoModel.getStatusPedido(),
                pedidoModel.getStatusPagamento(),
                pedidoModel.getValorTotal()
        );
        pedidoEntity.setProdutos(produtosList);
        pedidoEntity.setUuid(pedidoModel.getUuid());
        pedidoEntity.setNumeroPedido(pedidoModel.getNumeroPedido());
        return pedidoEntity;
    }

    public Pedido encontraPedidoShortPorUuid(UUID pedidoUuid, UUID clienteUuid) {
        PedidoModel pedidoModel = pedidoRepository.findByUuid(pedidoUuid);
        if (pedidoModel == null) {
            return null;
        }
        if (!pedidoModel.getClienteId().toString().equals(clienteUuid.toString())) {
            return null;
        }
        pedidoModel.setStatusPedido(StatusPedido.FINALIZADO);
        pedidoRepository.save(pedidoModel);

        Pedido pedidoEntity = new Pedido(
                pedidoModel.getUuid(),
                pedidoModel.getClienteId(),
                pedidoModel.getStatusPedido(),
                pedidoModel.getStatusPagamento(),
                pedidoModel.getValorTotal()
        );
        pedidoEntity.setUuid(pedidoModel.getUuid());
        pedidoEntity.setStatusPedido(StatusPedido.FINALIZADO);
        pedidoEntity.setNumeroPedido(pedidoModel.getNumeroPedido());
        return pedidoEntity;
    }
}
