package com.fiap.msclienteapi.application.controllers.cliente.identifica;


import com.fiap.msclienteapi.application.controllers.cliente.identifica.requests.InativacaoClienteRequest;
import com.fiap.msclienteapi.application.response.GenericResponse;
import com.fiap.msclienteapi.application.response.PresenterResponse;
import com.fiap.msclienteapi.domain.generic.output.OutputInterface;
import com.fiap.msclienteapi.domain.input.cliente.InativacaoClienteInput;
import com.fiap.msclienteapi.domain.output.cliente.InativacaoClienteOutput;
import com.fiap.msclienteapi.domain.presenters.cliente.identifica.InativacaoClientePresenter;
import com.fiap.msclienteapi.domain.useCase.cliente.InativacaoClienteUseCase;
import com.fiap.msclienteapi.infra.adpter.repository.cliente.IdentificarClienteRepository;
import com.fiap.msclienteapi.infra.queue.kafka.producers.InativarClienteProducer;
import com.fiap.msclienteapi.infra.repository.ClienteInativacaoRepository;
import com.fiap.msclienteapi.infra.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente/inativar-dados-pessoais")
public class InativacaoClienteController {
    private final ClienteRepository clienteRepository;
    private final ClienteInativacaoRepository clienteInativacaoRepository;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String servers;

    @PostMapping
    @Operation(tags = {"cliente"})
    public ResponseEntity<Object> inativacaoCliente(@RequestBody InativacaoClienteRequest identificaClienteRequest) throws Exception {

        InativacaoClienteInput identificaClienteInput = new InativacaoClienteInput(
            identificaClienteRequest.nome(),
            identificaClienteRequest.endereco(),
            identificaClienteRequest.numeroTelefone(),
            identificaClienteRequest.dadosDePagamento()
        );
        InativacaoClienteUseCase useCase = new InativacaoClienteUseCase(
            new IdentificarClienteRepository(clienteRepository, clienteInativacaoRepository),
            new InativarClienteProducer(servers)
        );
        useCase.execute(identificaClienteInput);

        OutputInterface outputInterface = useCase.getInativaClienteOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        InativacaoClientePresenter presenter = new InativacaoClientePresenter((InativacaoClienteOutput) outputInterface);

        return new PresenterResponse().response(presenter);
    }

}
