package com.fiap.msclienteapi.domain.useCase.cliente;

import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.exception.cliente.EmailNaoPodeSerNuloException;
import com.fiap.msclienteapi.domain.exception.cliente.NomeNaoPodeSerNuloException;
import com.fiap.msclienteapi.domain.exception.valueObject.documento.CpfInvalidoValueObjectException;
import com.fiap.msclienteapi.domain.gateway.cliente.IdentificarClienteInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputError;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.generic.output.OutputStatus;
import com.fiap.msclienteapi.domain.input.cliente.IdentificaClienteInput;
import com.fiap.msclienteapi.domain.output.cliente.IdentificaClienteOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IdentificarClienteUseCase {

    private final IdentificarClienteInterface identificaCliente;
    private OutputInterface identificaClienteOutput;

    public void execute(IdentificaClienteInput identificaClienteInput) throws Exception {
        Cliente cliente = new Cliente(
                identificaClienteInput.getNome(),
                identificaClienteInput.getCpf(),
                identificaClienteInput.getEmail(),
                null
        );
        try {
            Cliente clienteBusca = this.identificaCliente.buscaClientePorCpf(cliente.getCpf());
            if (clienteBusca == null) {
                cliente = cliente.identificarCliente();
                Cliente clienteEntity = this.identificaCliente.identificarCliente(cliente);
                this.identificaClienteOutput = new IdentificaClienteOutput(
                        clienteEntity,
                        new OutputStatus(201, "Created", "Cliente criado")
                );
                return;
            }

            this.identificaClienteOutput = new IdentificaClienteOutput(
                    clienteBusca,
                    new OutputStatus(200, "Ok", "Cliente encontrado")
            );

        } catch (NomeNaoPodeSerNuloException | EmailNaoPodeSerNuloException |
                 CpfInvalidoValueObjectException validationException) {
            this.identificaClienteOutput = new OutputError(
                    validationException.getMessage(),
                    new OutputStatus(422, "Unprocessable Entity", validationException.getMessage())
            );
        } catch (Exception e) {
            this.identificaClienteOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Error", e.getMessage())
            );
        }
    }
}
