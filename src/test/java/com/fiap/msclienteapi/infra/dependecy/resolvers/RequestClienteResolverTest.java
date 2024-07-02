package com.fiap.msclienteapi.infra.dependecy.resolvers;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestClienteResolverTest {

    @Test
    public void deveResolverUmCliente(){
        try {
            String authHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJ1c2VybmFtZSI6IkpvaG4gTWF5ZXIifQ.oG3HoHjb18DaK5BrjHbOdL7M-9vZJ-WGuljgCc6UPzw";
            String retorno = RequestClienteResolver.resolve(authHeader, UUID.randomUUID());
            assertThat(retorno).isEqualTo("John Mayer");
        } catch (Exception e) {}
    }

    @Test
    public void deveDevolverOUUIDdoClienteCasoNaoPossuaToken(){
        try {
            UUID clienteUuid = UUID.randomUUID();
            String retorno = RequestClienteResolver.resolve(null, clienteUuid);
            assertThat(retorno).isEqualTo(clienteUuid.toString());
        } catch (Exception e) {}
    }

    @Test
    public void deveDevolverNullAoChamarComAmbosVazios(){
        try {
            RequestClienteResolver resolver = new RequestClienteResolver();
            String retorno = resolver.resolve(null, null);
            assertThat(retorno).isNull();
        } catch (Exception e) {}
    }
}