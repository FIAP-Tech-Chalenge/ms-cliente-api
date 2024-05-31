package com.fiap.msclienteapi.domain.output.produto;

import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class BuscaTodosProdutoOutput implements OutputInterface {
    private List<Produto> listProdutos;
    private OutputStatus outputStatus;

    public BuscaTodosProdutoOutput(List<Produto> listProdutos, OutputStatus outputStatus) {
        this.listProdutos = listProdutos;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.listProdutos;
    }
}