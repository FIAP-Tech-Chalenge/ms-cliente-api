package com.fiap.msclienteapi.domain.output.produto;

import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscaTodosProdutoOutputTests {
    @Test
    public void deveInstanciarClasseCorretamente() {
        List<Produto> listaProdutoPedido = new ArrayList<>();
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        BuscaTodosProdutoOutput buscaProdutoOutput = new BuscaTodosProdutoOutput(listaProdutoPedido, outputStatus);

        assertThat(buscaProdutoOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(buscaProdutoOutput.getBody()).isNotNull();
    }
}
