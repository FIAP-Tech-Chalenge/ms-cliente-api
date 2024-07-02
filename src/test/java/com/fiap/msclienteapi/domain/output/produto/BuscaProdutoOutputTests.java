package com.fiap.msclienteapi.domain.output.produto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fiap.msclienteapi.domain.entity.produto.Produto;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;


public class BuscaProdutoOutputTests {
    @Test
    public void deveInstanciarClasseCorretamente() {
        Produto produto = new Produto(null, null, null, null, null);
        OutputStatus outputStatus= new OutputStatus(0, null, null);
        BuscaProdutoOutput buscaProdutoOutput = new BuscaProdutoOutput(produto, outputStatus);

        assertThat(buscaProdutoOutput.getOutputStatus().getCode()).isEqualTo(0);
        assertThat(buscaProdutoOutput.getBody()).isInstanceOf(Produto.class);
    }
}
