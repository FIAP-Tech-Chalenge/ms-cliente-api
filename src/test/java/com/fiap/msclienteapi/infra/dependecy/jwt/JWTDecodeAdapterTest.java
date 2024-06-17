package com.fiap.msclienteapi.infra.dependecy.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class JWTDecodeAdapterTest {

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    JWTDecodeAdapter jwtDecodeAdapter;

    @Test
    public void deveRetornarUmaMensagemCasoTokenPossuaMaisDeTresPartes() {
        String tokenComMaisDeTresPartes = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJ1c2VybmFtZSI6IkpvaG4gTWF5ZXIifQ.oG3HoHjb18DaK5BrjHbOdL7M-9vZJ-WGuljgCc6UPzw";
        try {
            String mensagemDeRetorno = jwtDecodeAdapter.claimClienteUuid(tokenComMaisDeTresPartes);
            assertThat(mensagemDeRetorno).isEqualTo("Invalid token format");
        } catch (Exception e) {}
    }

    @Test
    public void deveRetornarUsername() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJ1c2VybmFtZSI6IkpvaG4gTWF5ZXIifQ.oG3HoHjb18DaK5BrjHbOdL7M-9vZJ-WGuljgCc6UPzw";
        try {
            String mensagemDeRetorno = jwtDecodeAdapter.claimClienteUuid(token);
            assertThat(mensagemDeRetorno).isEqualTo("John Mayer");
        } catch (Exception e) {}
    }
}