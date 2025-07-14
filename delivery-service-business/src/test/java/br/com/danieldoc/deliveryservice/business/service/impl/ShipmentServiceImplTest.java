package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.event.ShipmentStatusUpdatedEvent;
import br.com.danieldoc.deliveryservice.business.fixture.ShipmentFixture;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import br.com.danieldoc.deliveryservice.domain.exception.DeliveryServiceBusinessException;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.ShipmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTest {

    private static final String SHIPMENT_ANY_CODE = "shipment_code";

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void testGivenGetDetail_WhenCodeExists_ThenShouldReturnShipment() {
        final Shipment expectedShipment = ShipmentFixture.createTestShipment(SHIPMENT_ANY_CODE);
        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(SHIPMENT_ANY_CODE))
                .thenReturn(Optional.of(expectedShipment));

        Shipment actualShipment = shipmentService.getDetail(SHIPMENT_ANY_CODE);

        Assertions.assertNotNull(actualShipment);
        Assertions.assertEquals(expectedShipment, actualShipment);
    }

    @Test
    void testGivenGetDetail_WhenCodeNotExists_ThenShouldThrowEntityNotFoundException() {
        final String invalidCode = "invalid_code";
        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(invalidCode)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            shipmentService.getDetail(invalidCode);
        });

        Assertions.assertTrue(exception.getMessage().contains("Entrega com o código invalid_code não encontrada"));
    }

    @Test
    void testGivenSave_WhenCreateNewShipment_ThenShouldSaveAndReturnShipment() {
        final Shipment shipmentToSave = ShipmentFixture.createTestShipment(null);
        shipmentToSave.setId(null);

        Mockito.when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(shipmentToSave);

        Shipment createdShipment = shipmentService.save(shipmentToSave);

        Assertions.assertNotNull(createdShipment);
        Mockito.verify(shipmentRepository).save(shipmentToSave);
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(ShipmentStatusUpdatedEvent.class));
    }

    @Test
    void testGivenSave_WhenUpdateExistingShipment_ThenShouldUpdateAndReturnExistingShipment() {
        final Shipment existingShipment = ShipmentFixture.createTestShipment(SHIPMENT_ANY_CODE);
        Mockito.when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(existingShipment);

        Shipment updatedShipment = shipmentService.save(existingShipment);

        Assertions.assertNotNull(updatedShipment);
        Assertions.assertEquals(1L, updatedShipment.getId());
        Mockito.verify(shipmentRepository).save(existingShipment);
        Mockito.verify(eventPublisher, Mockito.never()).publishEvent(Mockito.any());
    }

    @Test
    void testGivenSave_WhenUpdatingNonUpdatableShipment_ThenShouldThrowBusinessException() {
        final Shipment nonUpdatableShipment = ShipmentFixture.createTestShipment(SHIPMENT_ANY_CODE);
        nonUpdatableShipment.pickUp("TRACK123");

        DeliveryServiceBusinessException exception = Assertions.assertThrows(DeliveryServiceBusinessException.class, () -> {
            shipmentService.save(nonUpdatableShipment);
        });

        Assertions.assertTrue(exception.getMessage().contains("Entrega não pode ser atualizada pois seu status é:"));
        Mockito.verify(shipmentRepository, Mockito.never()).save(Mockito.any(Shipment.class));
    }

    @Test
    void testGivenDeleteByCode_WhenCodeExistsAndIsDeletable_ThenShouldMarkShipmentAsDeleted() {
        final Shipment shipment = ShipmentFixture.createTestShipment(SHIPMENT_ANY_CODE);
        Assertions.assertEquals(ShipmentStatus.CREATED, shipment.getStatus());

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(SHIPMENT_ANY_CODE)).thenReturn(Optional.of(shipment));

        shipmentService.deleteByCode(SHIPMENT_ANY_CODE);

        ArgumentCaptor<Shipment> shipmentCaptor = ArgumentCaptor.forClass(Shipment.class);
        Mockito.verify(shipmentRepository).save(shipmentCaptor.capture());

        Shipment savedShipment = shipmentCaptor.getValue();
        Assertions.assertTrue(savedShipment.isDeleted());
    }

    @Test
    void testGivenDeleteByCode_WhenShipmentIsNotDeletable_ThenShouldThrowBusinessException() {
        final Shipment nonDeletableShipment = ShipmentFixture.createTestShipment(SHIPMENT_ANY_CODE);
        nonDeletableShipment.pickUp("TRACK123");

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(SHIPMENT_ANY_CODE)).thenReturn(Optional.of(nonDeletableShipment));

        DeliveryServiceBusinessException exception = Assertions.assertThrows(DeliveryServiceBusinessException.class, () -> {
            shipmentService.deleteByCode(SHIPMENT_ANY_CODE);
        });

        Assertions.assertTrue(exception.getMessage().contains("Entrega não pode ser excluída pois seu status é:"));
        Mockito.verify(shipmentRepository, Mockito.never()).save(Mockito.any(Shipment.class));
    }

    @Test
    void testGivenDeleteByCode_WhenCodeNotExists_ThenShouldThrowEntityNotFoundException() {
        final String invalidCode = "invalid_code";
        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(invalidCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            shipmentService.deleteByCode(invalidCode);
        });
    }

    @Test
    void testGivenExistsByCodeOrFail_WhenCodeExists_ThenShouldNotThrowException() {
        Mockito.when(shipmentRepository.existsByCodeAndNotDeleted(SHIPMENT_ANY_CODE)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> {
            shipmentService.existsByCodeOrFail(SHIPMENT_ANY_CODE);
        });

        Mockito.verify(shipmentRepository).existsByCodeAndNotDeleted(SHIPMENT_ANY_CODE);
    }

    @Test
    void testGivenExistsByCodeOrFail_WhenCodeDoesNotExist_ThenShouldThrowEntityNotFoundException() {
        final String invalidCode = "invalid_code";
        Mockito.when(shipmentRepository.existsByCodeAndNotDeleted(invalidCode)).thenReturn(false);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            shipmentService.existsByCodeOrFail(invalidCode);
        });

        Assertions.assertTrue(exception.getMessage().contains("Entrega com o código invalid_code não encontrada"));
    }
}
