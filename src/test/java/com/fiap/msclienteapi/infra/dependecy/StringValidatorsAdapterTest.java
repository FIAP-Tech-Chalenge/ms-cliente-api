package com.fiap.msclienteapi.infra.dependecy;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class StringValidatorsAdapterTest {

    @Test
    public void deveValidarUUID(){
        StringValidatorsAdapter stringValidatorsAdapter = new StringValidatorsAdapter();
        boolean valido = stringValidatorsAdapter.isValidUUID("26929514-237c-11ed-861d-0242ac120002");
        assertThat(valido).isTrue();
    }

    @Test
    public void deveRetornarFalseAoReceberUmValorNulo(){
        boolean valido = StringValidatorsAdapter.isValidUUID(null);
        assertThat(valido).isFalse();
    }

    @Test
    public void deveRetornarFalseAoReceberUUIDInvalida(){
        boolean valido = StringValidatorsAdapter.isValidUUID("invalid");
        assertThat(valido).isFalse();
    }

    @Test
    public void deveConverterUmaStringParaUUID(){
        UUID uuid = StringValidatorsAdapter.toUUID("26929514-237c-11ed-861d-0242ac120002");
        assertInstanceOf(UUID.class, uuid);
    }

    @Test
    public void deveRetornarNullAoTentarConverterStringInvalidaParaUUID(){
        UUID uuid = StringValidatorsAdapter.toUUID("invalido");
        assertThat(uuid).isNull();
    }
}