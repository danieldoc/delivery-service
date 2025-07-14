package br.com.danieldoc.deliveryservice.domain.entity;

import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import br.com.danieldoc.deliveryservice.domain.exception.DeliveryServiceBusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ShipmentTest {

    private Shipment shipment;

    @BeforeEach
    void setUp() {
        shipment = Shipment.builder()
                .id(1L)
                .packageQuantity(2)
                .deliveryDeadline(OffsetDateTime.now().plusDays(5))
                .customer(new Customer("12345678900", "Cliente Teste", "11999998888", "cliente@teste.com"))
                .address(new Address("Praça da Sé", "s/n", "Sé", null, "São Paulo", "SP", "01001000", null))
                .build();
    }

    @Test
    void testGivenPickUp_WhenStatusIsCreated_ThenShouldUpdateStatusAndTrackingCode() {
        String trackingCode = "BR123456789";

        shipment.pickUp(trackingCode);

        Assertions.assertEquals(ShipmentStatus.PICKED_UP, shipment.getStatus());
        Assertions.assertEquals(trackingCode, shipment.getTrackingCode());
    }

    @Test
    void testGivenPickUp_WhenStatusIsInvalid_ThenShouldThrowException() {
        shipment.pickUp("BR123456789");

        DeliveryServiceBusinessException exception = Assertions.assertThrows(DeliveryServiceBusinessException.class, () ->
                shipment.pickUp("outro-codigo"));

        Assertions.assertTrue(exception.getMessage().contains("Não é permitido alterar o status de"));
    }

    @Test
    void testGivenDeliver_WhenStatusIsPickedUp_ThenShouldUpdateStatusAndDeliveryInfo() {
        shipment.pickUp("BR123456789");
        String receiverName = "Maria Silva";

        shipment.deliver(receiverName);

        Assertions.assertEquals(ShipmentStatus.DELIVERED, shipment.getStatus());
        Assertions.assertEquals(receiverName, shipment.getReceiverName());
        Assertions.assertNotNull(shipment.getDeliveredAt());
    }

    @Test
    void testGivenDeliver_WhenStatusIsInvalid_ThenShouldThrowException() {
        DeliveryServiceBusinessException exception = Assertions.assertThrows(DeliveryServiceBusinessException.class, () ->
                shipment.deliver("João Ninguém"));

        Assertions.assertTrue(exception.getMessage().contains("Não é permitido alterar o status de"));
    }

    @Test
    void testGivenCancel_WhenStatusIsCreated_ThenShouldUpdateStatusAndCancellationInfo() {
        String reason = "Cliente desistiu da compra.";

        shipment.cancel(reason);

        Assertions.assertEquals(ShipmentStatus.CANCELLED, shipment.getStatus());
        Assertions.assertEquals(reason, shipment.getCancellationReason());
        Assertions.assertNotNull(shipment.getCanceledAt());
    }

    @Test
    void testGivenCancel_WhenStatusIsPickedUp_ThenShouldUpdateStatusAndCancellationInfo() {
        shipment.pickUp("BR123456789");
        String reason = "Pacote avariado durante o transporte.";

        shipment.cancel(reason);

        Assertions.assertEquals(ShipmentStatus.CANCELLED, shipment.getStatus());
        Assertions.assertEquals(reason, shipment.getCancellationReason());
        Assertions.assertNotNull(shipment.getCanceledAt());
    }

    @Test
    void testGivenCancel_WhenStatusIsDelivered_ThenShouldThrowException() {
        shipment.pickUp("BR123456789");
        shipment.deliver("Maria Silva");

        DeliveryServiceBusinessException exception = Assertions.assertThrows(DeliveryServiceBusinessException.class, () ->
                shipment.cancel("Tentei cancelar atrasado"));

        Assertions.assertTrue(exception.getMessage().contains("Não é permitido alterar o status de"));
    }

    @Test
    void testGivenDeleteIt_WhenCalled_ThenShouldSetDeletedFlagToTrue() {
        Assertions.assertFalse(shipment.isDeleted());

        shipment.deleteIt();

        Assertions.assertTrue(shipment.isDeleted());
    }

    @Test
    void testGivenCantBeUpdatedOrDeleted_WhenIdIsNull_ThenShouldReturnFalse() {
        Shipment newShipment = Shipment.builder().build();

        boolean result = newShipment.cantBeUpdatedOrDeleted();

        Assertions.assertFalse(result);
    }

    @Test
    void testGivenCantBeUpdatedOrDeleted_WhenStatusIsCreatedAndIdIsNotNull_ThenShouldReturnFalse() {
        boolean result = shipment.cantBeUpdatedOrDeleted();

        Assertions.assertFalse(result);
    }

    @Test
    void testGivenCantBeUpdatedOrDeleted_WhenStatusIsNotCreatedAndIdIsNotNull_ThenShouldReturnTrue() {
        shipment.pickUp("BR123456789");

        boolean result = shipment.cantBeUpdatedOrDeleted();

        Assertions.assertTrue(result);
    }
}