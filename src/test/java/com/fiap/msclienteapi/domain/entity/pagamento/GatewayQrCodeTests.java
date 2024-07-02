package com.fiap.msclienteapi.domain.entity.pagamento;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class GatewayQrCodeTests {

    @Test
    public void deveInstanciarGatewayQrCodeDiretamente() {
        GatewayQrCode gatewayQrCode = new GatewayQrCode("data", "ordered");
        assertInstanceOf(GatewayQrCode.class, gatewayQrCode);
        assertThat(gatewayQrCode.getQrData()).isEqualTo("data");
        assertThat(gatewayQrCode.getInStoreOrderId()).isEqualTo("ordered");
    }
}
