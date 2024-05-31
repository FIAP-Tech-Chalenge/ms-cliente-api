package com.fiap.msclienteapi.domain.entity.produto;

import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Data
@Getter
@Setter
public class Produto {
    private UUID uuid;
    private String nome;
    private Float valor;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Integer quantidade;
    private List<Imagem> imagens;

    public Produto(String nome, Float valor, String descricao, CategoriaEnum categoria, Integer quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }
}
