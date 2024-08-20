package com.fiap.msclienteapi.infra.repository;

import com.fiap.msclienteapi.infra.model.ClienteInativoModel;
import com.fiap.msclienteapi.infra.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ClienteInativacaoRepository extends JpaRepository<ClienteInativoModel, Long> {
}
