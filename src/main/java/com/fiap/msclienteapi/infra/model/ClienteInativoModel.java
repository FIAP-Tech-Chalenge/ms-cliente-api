package com.fiap.msclienteapi.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="clientes_inativacao")
public class ClienteInativoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(name = "uuid_cliente")
    private UUID uuidCliente;
    @Column(name = "data_inativacao")
    private Date dataInativacao;

    public ClienteInativoModel(UUID uuid) {
        this.uuidCliente = uuid;
        this.dataInativacao = new Date();
    }

}
