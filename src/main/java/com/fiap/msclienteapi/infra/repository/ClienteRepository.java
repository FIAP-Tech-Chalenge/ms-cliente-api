package com.fiap.msclienteapi.infra.repository;

import com.fiap.msclienteapi.infra.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    ClienteModel findByCpf(String cpf);

    ClienteModel findByUuid(UUID uuid);

}
