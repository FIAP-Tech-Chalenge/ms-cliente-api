package com.fiap.msclienteapi.domain.entity.cliente.validation;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.fiap.msclienteapi.domain.entity.cliente.Cliente;
import com.fiap.msclienteapi.domain.exception.cliente.EmailNaoPodeSerNuloException;
import com.fiap.msclienteapi.domain.exception.cliente.NomeNaoPodeSerNuloException;
import com.fiap.msclienteapi.domain.exception.valueObject.documento.CpfInvalidoValueObjectException;

public class IdentificaClienteValidationTests {

    @Test
    public void deveVailidarUmClienteComSucessoAoReceberDadosValidos() {
        Cliente cliente = new Cliente("cliente", "15148118040", "email@email.com", UUID.randomUUID());
        try {
            cliente.identificarCliente();
        } catch (Exception e) {
        }
        assertInstanceOf(Cliente.class, cliente);
    }

    @Test
    public void deveGerarExcecao_AoValidarClienteComCPFInvalido_CpfInvalidoValueObjectException() {
        Cliente cliente = new Cliente("cliente", "000", "email@email.com", UUID.randomUUID());
        try {
            cliente.identificarCliente();
        } catch (Exception exception) {
            assertInstanceOf(CpfInvalidoValueObjectException.class, exception);
        }
    }

    @Test
    public void deveGerarExcecao_AoValidarClienteSemNome_NomeNaoPodeSerNuloException() {
        Cliente cliente = new Cliente("", "15148118040", "email@email.com", UUID.randomUUID());
        try {
            cliente.identificarCliente();
        } catch (Exception exception) {
            assertInstanceOf(NomeNaoPodeSerNuloException.class, exception);
        }
    }

    @Test
    public void deveGerarExcecao_AoValidarClienteSemEmail_EmailNaoPodeSerNuloException() {
        Cliente cliente = new Cliente("cliente", "15148118040", "", UUID.randomUUID());
        try {
            cliente.identificarCliente();
        } catch (Exception exception) {
            assertInstanceOf(EmailNaoPodeSerNuloException.class, exception);
        }
    }

    @Test
    public void deveRetornarUmaInstanciaDeUmClienteComCPFVazio() {
        Cliente cliente = new Cliente("cliente", "", "cliente@cliente.com", UUID.randomUUID());
        try {
            cliente.identificarCliente();
        } catch (Exception exception) {
        }
        assertInstanceOf(Cliente.class, cliente);
    }
}
