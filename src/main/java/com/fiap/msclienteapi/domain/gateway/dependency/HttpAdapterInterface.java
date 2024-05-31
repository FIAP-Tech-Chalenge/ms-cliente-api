package com.fiap.msclienteapi.domain.gateway.dependency;

import java.util.Map;

public interface HttpAdapterInterface {
    String get(String url, Map<String, String> headers) throws Exception;

    String post(String url, String requestBody, Map<String, String> headers) throws Exception;
}
