package com.fiap.msclienteapi.domain.useCase.checkout;

import com.fiap.msclienteapi.domain.entity.pagamento.GatewayQrCode;
import com.fiap.msclienteapi.domain.entity.pedido.Checkout;
import com.fiap.msclienteapi.domain.entity.pedido.Pedido;
import com.fiap.msclienteapi.domain.exception.checkout.PedidoNaoEncontradoException;
import com.fiap.msclienteapi.domain.gateway.checkout.CheckoutProcessorInterface;
import com.fiap.msclienteapi.domain.gateway.pagamento.PagamentoQrCodeInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.output.pedido.CheckOutOutput;
import com.fiap.msclienteapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProcessaCheckoutUseCase {
    private final BuscarPedidoRepository buscarPedidoRepository;
    private final CheckoutProcessorInterface checkoutProcessor;
    private final PagamentoQrCodeInterface pagamentoQrCodeInterface;
    private OutputInterface checkoutOutput;

    @Transactional
    public void execute(UUID pedidoUuid) {
        try {
            Pedido pedido = buscarPedidoRepository.encontraPedidoPorUuid(pedidoUuid, null);
            if (pedido == null) {
                throw new PedidoNaoEncontradoException("Pedido não encontrado");
            }

            Checkout checkout = checkoutProcessor.processarCheckout(pedido);
            GatewayQrCode gatewayQrCode = this.pagamentoQrCodeInterface.geraQrCodePagamento(pedidoUuid, pedido.getTotal());
            checkout.setQrCode(gatewayQrCode.getQrData());
            checkoutOutput = new CheckOutOutput(
                    checkout,
                    new OutputStatus(200, "Ok", "Checkout realizado com sucesso")
            );
        } catch (PedidoNaoEncontradoException pedidoNaoEncontradoException) {
            this.checkoutOutput = new OutputError(
                    pedidoNaoEncontradoException.getMessage(),
                    new OutputStatus(404, "Not Found", "Produto não encontrado")
            );
        }
    }
}
