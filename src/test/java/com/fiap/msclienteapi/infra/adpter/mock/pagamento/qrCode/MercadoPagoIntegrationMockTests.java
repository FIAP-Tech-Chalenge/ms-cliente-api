package com.fiap.msclienteapi.infra.adpter.mock.pagamento.qrCode;

import com.fiap.msclienteapi.domain.gateway.dependency.HttpAdapterInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MercadoPagoIntegrationMockTests {

    @Mock
    HttpAdapterInterface httpAdapterInterface;
    
    MercadoPagoIntegrationMock mercadoPagoIntegrationMock;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        mercadoPagoIntegrationMock = new MercadoPagoIntegrationMock(httpAdapterInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}