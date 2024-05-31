package com.fiap.msclienteapi.domain.gateway.pagamento;

import com.fiap.msclienteapi.domain.entity.pagamento.GatewayQrCode;

import java.util.UUID;

public interface PagamentoQrCodeInterface {
    GatewayQrCode geraQrCodePagamento(UUID uuid, Float valorTotal);
}
