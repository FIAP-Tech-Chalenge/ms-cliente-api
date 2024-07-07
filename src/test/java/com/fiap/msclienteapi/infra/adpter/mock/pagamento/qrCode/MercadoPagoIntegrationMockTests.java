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

    // @Test
    // public void deveGerarQrCode() {
    //     GatewayQrCode mercadoPagoQRCode = mercadoPagoIntegrationMock.geraQrCodePagamento(UUID.randomUUID(), 10.0f);
    //     assertThat(mercadoPagoQRCode.getQrData()).isEqualTo("00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D");
    // }
}