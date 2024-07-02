package com.fiap.msclienteapi.domain.presenters.cliente.pagamento;

import com.fiap.msclienteapi.domain.output.pagamento.StatusPagamentoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StatusPagamentoPresenterTests {
        
    @Mock
    StatusPagamentoOutput statusPagamentoOutput;
    
    StatusPagamentoPresenter statusPagamentoPresenter;

    AutoCloseable openMocks;
    
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        statusPagamentoPresenter = new StatusPagamentoPresenter(statusPagamentoOutput);
    }

    @Test
    public void deveObterUmOutput() {
        // TODO: Reimplementar teste
    }

    @Test
    void deveRetornarUmArrayDeCliente() {
        // TODO: Reimplementar teste
    }
}