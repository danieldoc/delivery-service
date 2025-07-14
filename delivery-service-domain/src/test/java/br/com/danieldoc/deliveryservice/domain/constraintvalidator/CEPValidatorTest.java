package br.com.danieldoc.deliveryservice.domain.constraintvalidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CEPValidatorTest {

    private CEPValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CEPValidator();
    }

    @Test
    void testGivenIsValid_WhenCepIsNullOrBlank_ThenShouldReturnTrue() {
        Assertions.assertTrue(validator.isValid(null, null));
        Assertions.assertTrue(validator.isValid("", null));
        Assertions.assertTrue(validator.isValid("   ", null));
    }

    @ParameterizedTest
    @ValueSource(strings = { "01001000", "12345678", "98765432" })
    void testGivenIsValid_WhenCepIsValid_ThenShouldReturnTrue(String validCep) {
        boolean result = validator.isValid(validCep, null);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567",       // Comprimento incorreto (menor)
            "123456789",     // Comprimento incorreto (maior)
            "12345-678",     // Contém formatação
            "abcdefgh",      // Contém letras
            "1234567a"       // Contém letra no final
    })
    void testGivenIsValid_WhenCepIsInvalid_ThenShouldReturnFalse(String invalidCep) {
        boolean result = validator.isValid(invalidCep, null);
        Assertions.assertFalse(result);
    }
}