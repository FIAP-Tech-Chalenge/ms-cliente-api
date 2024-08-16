package com.fiap.msclienteapi.infra.adpter.integration.pagamento.qrCode;

import com.fiap.msclienteapi.domain.gateway.dependency.HttpAdapterInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}