package br.com.danieldoc.deliveryservice.domain.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class ShipmentStatusTest {

    @Test
    void testGivenGetDescription_WhenCalled_ThenShouldReturnCorrectDescription() {
        Assertions.assertEquals("Criado", ShipmentStatus.CREATED.getDescription());
        Assertions.assertEquals("Coletado pelo transportador", ShipmentStatus.PICKED_UP.getDescription());
        Assertions.assertEquals("Entregue", ShipmentStatus.DELIVERED.getDescription());
        Assertions.assertEquals("Cancelado", ShipmentStatus.CANCELLED.getDescription());
    }

    @ParameterizedTest
    @CsvSource({
            "CREATED, PICKED_UP",
            "CREATED, CANCELLED",
            "PICKED_UP, DELIVERED",
            "PICKED_UP, CANCELLED"
    })
    void testGivenCanTransitionTo_WhenTransitionIsValid_ThenShouldReturnTrue(ShipmentStatus from, ShipmentStatus to) {
        boolean canTransition = from.canTransitionTo(to);
        Assertions.assertTrue(canTransition);
    }

    @ParameterizedTest
    @CsvSource({
            "CREATED, DELIVERED",
            "PICKED_UP, CREATED",
            "DELIVERED, CREATED",
            "DELIVERED, PICKED_UP",
            "CANCELLED, CREATED",
            "CANCELLED, PICKED_UP"
    })
    void testGivenCanTransitionTo_WhenTransitionIsInvalid_ThenShouldReturnFalse(ShipmentStatus from, ShipmentStatus to) {
        boolean canTransition = from.canTransitionTo(to);
        Assertions.assertFalse(canTransition);
    }

    @ParameterizedTest
    @EnumSource(ShipmentStatus.class)
    void testGivenCanTransitionTo_WhenTransitioningToSelf_ThenShouldReturnFalse(ShipmentStatus status) {
        boolean canTransition = status.canTransitionTo(status);
        Assertions.assertFalse(canTransition);
    }

    @ParameterizedTest
    @CsvSource({
            "CREATED, DELIVERED",
            "PICKED_UP, CREATED",
            "DELIVERED, PICKED_UP",
            "CANCELLED, CREATED"
    })
    void testGivenCantTransitionTo_WhenTransitionIsInvalid_ThenShouldReturnTrue(ShipmentStatus from, ShipmentStatus to) {
        boolean cantTransition = from.cantTransitionTo(to);
        Assertions.assertTrue(cantTransition);
    }

    @ParameterizedTest
    @CsvSource({
            "CREATED, PICKED_UP",
            "CREATED, CANCELLED",
            "PICKED_UP, DELIVERED",
            "PICKED_UP, CANCELLED"
    })
    void testGivenCantTransitionTo_WhenTransitionIsValid_ThenShouldReturnFalse(ShipmentStatus from, ShipmentStatus to) {
        boolean cantTransition = from.cantTransitionTo(to);
        Assertions.assertFalse(cantTransition);
    }
}