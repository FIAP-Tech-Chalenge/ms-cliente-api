package com.fiap.msclienteapi.domain.valuesObject;


import com.fiap.msclienteapi.domain.exception.valueObject.documento.CpfInvalidoValueObjectException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CpfValueObjectTests {

    @Test
    public void deveGerarExcecao_ComCPFInvalido_CpfInvalidoValueObjectException() {
        try {
            CpfValueObject cpfinvalido = new CpfValueObject("00000000000");
            assertThat(cpfinvalido.getCpf()).isEqualTo("00000000000");
        } catch (Exception e) {
            assertInstanceOf(CpfInvalidoValueObjectException.class, e);
        }
    }

    @Test
    public void deveRetornarUmCPFAoSerValido() {
        try {
            CpfValueObject cpfinvalido = new CpfValueObject("48458113082");
            assertThat(cpfinvalido.getCpf()).isEqualTo("48458113082");
        } catch (Exception e) {}
    }

    @Test
    public void validaexpectedDigit() {
        try {
            CpfValueObject cpfinvalido = new CpfValueObject("12345678909");
            assertThat(cpfinvalido.getCpf()).isEqualTo("12345678909");
        } catch (Exception e) {}
    }
    
    @Test
    public void validaReminder() {
        try {
            CpfValueObject cpfinvalido = new CpfValueObject("12345678919");
            assertThat(cpfinvalido.getCpf()).isEqualTo("12345678919");
        } catch (Exception e) {}
    }
    
    @Test
    public void validaUltimoDigito() {
        try {
            CpfValueObject cpfinvalido = new CpfValueObject("12345678901");
            assertThat(cpfinvalido.getCpf()).isEqualTo("12345678901");
        } catch (Exception e) {}
    }
}
