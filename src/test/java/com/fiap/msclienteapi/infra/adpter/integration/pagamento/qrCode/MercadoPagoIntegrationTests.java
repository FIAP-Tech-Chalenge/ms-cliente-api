package com.fiap.msclienteapi.infra.adpter.integration.pagamento.qrCode;

import com.fiap.msclienteapi.domain.entity.pagamento.GatewayQrCode;
import com.fiap.msclienteapi.domain.gateway.dependency.HttpAdapterInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MercadoPagoIntegrationTests {

    @Mock
    HttpAdapterInterface httpAdapterInterface;
    
    MercadoPagoIntegration mercadoPagoIntegration;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        mercadoPagoIntegration = new MercadoPagoIntegration(httpAdapterInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void deveRetornarUmGatewayQRCODE() {
        UUID uuid = UUID.randomUUID();
        Float valorTotal = 100.0f;

        String jsonResponse = "{\"qr_data\": \"qr_code_simulado\", \"in_store_order_id\": \"order_id_simulado\"}";
        try {
            when(httpAdapterInterface.post(anyString(), anyString(), anyMap())).thenReturn(jsonResponse);
            GatewayQrCode qrCode = mercadoPagoIntegration.geraQrCodePagamento(uuid, valorTotal);   
            assertEquals("qr_code_simulado", qrCode.getQrData());
            assertEquals("order_id_simulado", qrCode.getInStoreOrderId());
        } catch (Exception e) {}
    }

    @Test
    public void testaSneakyThrows() {
        UUID uuid = UUID.randomUUID();
        Float valorTotal = 100.0f;
        Exception exception = assertThrows(Exception.class, () -> mercadoPagoIntegration.geraQrCodePagamento(uuid, valorTotal));
        assertEquals( exception.getMessage(), "Cannot invoke \"java.util.Map.get(Object)\" because \"dataResponse\" is null");
    }
}