package com.fiap.msclienteapi.application.controllers.cliente.identifica;


import com.fiap.msclienteapi.application.controllers.cliente.identifica.requests.IdentificaClienteRequest;
import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.input.cliente.IdentificaClienteInput;
import com.fiap.msclienteapi.domain.output.cliente.IdentificaClienteOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.identifica.IdentificaClientePresenter;
import com.fiap.msclienteapi.domain.useCase.cliente.IdentificarClienteUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.cliente.IdentificarClienteRepository;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import com.fiap.msclienteapi.infra.stream.producers.NovoClienteProducer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente/identificar")
public class ClienteController {


    private final ClienteRepository clienteRepository;

    @PostMapping
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> identificaCliente(@RequestBody IdentificaClienteRequest identificaClienteRequest) throws Exception {

        IdentificaClienteInput identificaClienteInput = new IdentificaClienteInput(
                identificaClienteRequest.nome(),
                identificaClienteRequest.cpf(),
                identificaClienteRequest.email()
        );
        IdentificarClienteUseCase useCase = new IdentificarClienteUseCase(
                new IdentificarClienteRepository(clienteRepository),
                new NovoClienteProducer()
        );
        useCase.execute(identificaClienteInput);

        OutputInterface outputInterface = useCase.getIdentificaClienteOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        IdentificaClientePresenter presenter = new IdentificaClientePresenter((IdentificaClienteOutput) outputInterface);

        return new PresenterResponse().response(presenter);
    }

}
