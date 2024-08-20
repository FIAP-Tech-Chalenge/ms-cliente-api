package com.fiap.msclienteapi.domain.useCase.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.gateway.cliente.IdentificarClienteInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.input.cliente.InativacaoClienteInput;
import com.fiap.msclienteapi.domain.output.cliente.InativacaoClienteOutput;
import lombok.Getter;

@Getter
public class InativacaoClienteUseCase {
    private final IdentificarClienteInterface idenificaCliente;
    private OutputInterface inativaClienteOutput;

    public InativacaoClienteUseCase(IdentificarClienteInterface idenificaCliente) {
        this.idenificaCliente = idenificaCliente;
    }

    public void execute(InativacaoClienteInput inativacaoClienteInput) throws Exception {
        Cliente cliente = new Cliente(
            inativacaoClienteInput.getNome(),
            null,
            null,
            null
        );
        try {
            Cliente clienteBusca = this.idenificaCliente.buscaClientePorNome(cliente.getNome());
            if (clienteBusca == null) {
                /*
                    resposa sempre 200 mesmo que não haja cliente para evitar ataques de força bruta para descobrir se um cliente existe
                    e assim evitar vazamento de informações
                */
                this.inativaClienteOutput = new InativacaoClienteOutput(
                    cliente,
                    new OutputStatus(200, "Ok", "Solicitacao de inativacao realizada")
                );
                return;
            }

            this.idenificaCliente.inativarCliente(clienteBusca);
            this.inativaClienteOutput = new InativacaoClienteOutput(
                clienteBusca,
                new OutputStatus(200, "Ok", "Solicitacao de inativacao realizada")
            );

        } catch (Exception e) {
            this.inativaClienteOutput = new OutputError(
                e.getMessage(),
                new OutputStatus(500, "Internal Error", e.getMessage())
            );
        }
    }
}
