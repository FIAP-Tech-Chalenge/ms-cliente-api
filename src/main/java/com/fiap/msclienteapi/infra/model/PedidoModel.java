package com.fiap.msclienteapi.infra.model;

import com.fiap.msclienteapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msclienteapi.domain.enums.pedido.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="pedidos")
public class PedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;
    @Column(name = "numeropedido")
    private Long numeroPedido;
    @Column(name = "clienteid")
    private UUID clienteId;
    @Column(name = "datacriacao")
    private Date dataCriacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "statuspedido")
    private StatusPedido statusPedido;
    @Enumerated(EnumType.STRING)
    @Column(name = "statuspagamento")
    private StatusPagamento statusPagamento;
    @Column(name= "tempo_preparo")
    private int tempoDePreparo;
    @Column(name = "valortotal")
    private Float valorTotal;

}

