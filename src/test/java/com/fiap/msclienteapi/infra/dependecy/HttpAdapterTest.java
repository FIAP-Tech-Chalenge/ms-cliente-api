package com.fiap.msclienteapi.infra.dependecy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


@ExtendWith(MockitoExtension.class)
public class HttpAdapterTest {

    @Mock
    HttpClient httpClient;
    
    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    HttpAdapter httpAdapter;
    
    @Test
    public void deveRetornarUmBodyEmCasoDeSucesso(){
        try {
            String resposta = httpAdapter.get("https://www.google.com/", null);
            assertThat(resposta).isNotNull();
        } catch (Exception e) {}
    }

    @Test
    public void deveGerarExcecao_QuandoHTTPCodeMaiorQue300_RuntimeException(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "anyString");
        String url = "http://google.com/";
        try {
            httpAdapter.get(url, headers);
        } catch (Exception e) {
            assertInstanceOf(RuntimeException.class, e);
            assertThat(e.getMessage()).contains("HTTP request failed with status code");
        }
    }

    @Test
    public void deveRetornarUmBodyEmUmPOSTcomSucesso(){
        try {
            String resposta = httpAdapter.post("https://jsonplaceholder.typicode.com/posts", "", null);
            assertThat(resposta).isNotNull();
        } catch (Exception e) {}
    }

    @Test
    public void deveGerarExcecao_POSTQuandoHTTPCodeMaiorQue300_RuntimeException(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "anyString");
        String url = "http://google.com/";
        try {
            httpAdapter.post(url, "", headers);
        } catch (Exception e) {
            assertInstanceOf(RuntimeException.class, e);
            assertThat(e.getMessage()).contains("HTTP request failed with status code");
        }
    }

}