package com.fiap.msclienteapi.infra.dependecy.jwt;

public interface JWTDecodeInterface {
    String claimClienteUuid(String token) throws Exception;
}
