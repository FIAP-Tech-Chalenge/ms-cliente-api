package com.fiap.msclienteapi.infra.repository;

import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import com.fiap.msclienteapi.infra.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, UUID> {

    PedidoModel findByUuid(UUID uuid);

    List<PedidoModel> findAll();

    @NonNull
    @Query("SELECT p FROM PedidoModel p WHERE p.statusPedido != com.fiap.msclienteapi.domain.enums.pedido.StatusPedido.FINALIZADO " +
            "ORDER BY CASE p.statusPedido " +
            "WHEN com.fiap.msclienteapi.domain.enums.pedido.StatusPedido.RECEBIDO THEN 0 " +
            "WHEN com.fiap.msclienteapi.domain.enums.pedido.StatusPedido.EM_PREPARACAO THEN 1 " +
            "WHEN com.fiap.msclienteapi.domain.enums.pedido.StatusPedido.PRONTO THEN 2 " +
            "ELSE 3 END")
    List<PedidoModel> findListaPedido();


    @Query("SELECT COALESCE(MAX(p.numeroPedido), 0) FROM PedidoModel p")
    Long findMaxNumeroPedido();

    List<PedidoModel> findByStatusPedido(StatusPedido statusPedido);
}
