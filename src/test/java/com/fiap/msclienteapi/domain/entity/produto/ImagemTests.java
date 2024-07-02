package com.fiap.msclienteapi.domain.entity.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ImagemTests {

    @Test
	public void deveInstanciarUmaImagem() {
        Imagem imagem = new Imagem("imagem.png", "http:");

        assertEquals("imagem.png", imagem.nome());
        assertEquals("http:", imagem.url());
    }
}
