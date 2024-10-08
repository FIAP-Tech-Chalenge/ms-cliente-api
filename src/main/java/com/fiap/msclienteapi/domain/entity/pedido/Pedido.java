package com.fiap.msclienteapi.domain.entity.pedido;

import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.domain.exception.pedido.PedidoVazioException;
import com.fiap.msclienteapi.domain.exception.pedido.ProdutoDoPedidoSemQuantidadeException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Pedido {
    private final UUID clienteUuid;
    private UUID uuid;
    private Long numeroPedido;
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;
    private List<Produto> produtos;
    private Integer tempoDePreparoEmMinutos;
    private Float total;

    public Pedido(UUID clienteUuid) {
        this.clienteUuid = clienteUuid;
        this.produtos = new ArrayList<>();
    }

    public Pedido(UUID pedidoId, UUID clienteId, StatusPedido statusPedido, StatusPagamento statusPagamento, Integer tempoDePreparoEmMinutos, Float valorTotal) {
        this.uuid = pedidoId;
        this.clienteUuid = clienteId;
        this.statusPedido = statusPedido;
        this.statusPagamento = statusPagamento;
        this.tempoDePreparoEmMinutos = tempoDePreparoEmMinutos;
        this.total = valorTotal;
    }

    public void addProduto(Produto produto) {
        produtos.add(produto);
    }

    public void verificaItensDoPedido() throws PedidoVazioException, ProdutoDoPedidoSemQuantidadeException {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getQuantidade() < 1) {
                throw new ProdutoDoPedidoSemQuantidadeException("Produto com quantidade inválida");
            }
        }
        if (produtos.isEmpty()) {
            throw new PedidoVazioException("Pedido vazio");
        }
    }

    public float valorTotalDoPedido() {
        float total = (float) 0;
        for (Produto produto : produtos) {
            total += produto.getValor() * produto.getQuantidade();
        }

        return total;
    }

}
