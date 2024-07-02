package com.fiap.msclienteapi.domain.input.pedido;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.fiap.msclienteapi.domain.enums.produto.CategoriaEnum;

public class ProdutoPedidoInputTests {
    
    @Test
    public void deveInstanciarClasseCorretamente() {
        UUID produtoUuid = UUID.randomUUID();
        ProdutoPedidoInput produtoPedidoInput = new ProdutoPedidoInput(produtoUuid, 1, CategoriaEnum.BEBIDA);
        
        assertThat(produtoPedidoInput.uuid()).isEqualTo(produtoUuid);
        assertThat(produtoPedidoInput.quantidade()).isEqualTo(1);
        assertThat(produtoPedidoInput.categoria()).isEqualTo(CategoriaEnum.BEBIDA);
    }
}