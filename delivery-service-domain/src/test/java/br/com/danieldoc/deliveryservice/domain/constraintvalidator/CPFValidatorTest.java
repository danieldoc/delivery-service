package br.com.danieldoc.deliveryservice.domain.constraintvalidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CPFValidatorTest {

    private CPFValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CPFValidator();
    }

    @Test
    void testGivenIsValid_WhenCpfIsNullOrBlank_ThenShouldReturnTrue() {
        Assertions.assertTrue(validator.isValid(null, null));
        Assertions.assertTrue(validator.isValid("", null));
        Assertions.assertTrue(validator.isValid("   ", null));
    }

    @ParameterizedTest
    @ValueSource(strings = { "87561119089", "52998224725", "50603415008" })
    void testGivenIsValid_WhenCpfIsValid_ThenShouldReturnTrue(String validCpf) {
        boolean result = validator.isValid(validCpf, null);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890",      // Comprimento incorreto (menor)
            "123456789012",    // Comprimento incorreto (maior)
            "111.222.333-44",  // Contém formatação
            "abcdefghijk",     // Contém letras
            "11111111111",     // Todos os dígitos iguais
            "22222222222",     // Todos os dígitos iguais
            "52998224726",     // Dígito verificador inválido
            "88403589034"      // Dígito verificador inválido
    })
    void testGivenIsValid_WhenCpfIsInvalid_ThenShouldReturnFalse(String invalidCpf) {
        boolean result = validator.isValid(invalidCpf, null);
        Assertions.assertFalse(result);
    }
}