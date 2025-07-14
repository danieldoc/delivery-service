package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.fixture.ShipmentFixture;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTest {

    public static final String SHIPMENT_ANY_CODE = "shipment_code";

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void testGivenGetDetail_WhenCodeExists_ThenShouldReturnShipment() {
        final String validCode = SHIPMENT_ANY_CODE;
        final Shipment expectedShipment = ShipmentFixture.createTestShipment(validCode);

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(validCode))
                .thenReturn(Optional.of(expectedShipment));

        Shipment actualShipment = shipmentService.getDetail(validCode);

        Assertions.assertNotNull(actualShipment);
        Assertions.assertEquals(expectedShipment, actualShipment);
    }

    @Test
    void testGivenGetDetail_WhenCodeNotExists_ThenShouldThrowEntityNotFoundException() {
        final String invalidCode = "invalid_code";
        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(invalidCode))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> shipmentService.getDetail(invalidCode));
    }

    @Test
    void testGivenSave_WhenCreateNewShipment_ThenShouldSaveAndReturnShipment() {
        final Shipment shipmentToSave = ShipmentFixture.createTestShipment(null);
        shipmentToSave.setId(null);

        Mockito.when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipmentToSave);

        Shipment createdShipment = shipmentService.save(shipmentToSave);

        Assertions.assertNotNull(createdShipment);
        Mockito.verify(shipmentRepository).save(shipmentToSave);
    }

    @Test
    void testGivenSave_WhenUpdateExistingShipment_ThenShouldUpdateAndReturnExistingShipment() {
        final Shipment existingShipment = ShipmentFixture.createTestShipment(SHIPMENT_ANY_CODE);

        Mockito.when(shipmentRepository.save(any(Shipment.class))).thenReturn(existingShipment);

        Shipment updatedShipment = shipmentService.save(existingShipment);

        Assertions.assertNotNull(updatedShipment);
        Assertions.assertEquals(1L, updatedShipment.getId());
        Mockito.verify(shipmentRepository).save(existingShipment);
    }

    @Test
    void testGivenDeleteByCode_WhenCodeExists_ThenShouldMarkShipmentAsDeleted() {
        final String codeToDelete = SHIPMENT_ANY_CODE;
        final Shipment shipment = ShipmentFixture.createTestShipment(codeToDelete);

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(codeToDelete)).thenReturn(Optional.of(shipment));

        shipmentService.deleteByCode(codeToDelete);

        ArgumentCaptor<Shipment> shipmentCaptor = ArgumentCaptor.forClass(Shipment.class);
        Mockito.verify(shipmentRepository).save(shipmentCaptor.capture());

        Shipment savedShipment = shipmentCaptor.getValue();
        Assertions.assertTrue(savedShipment.isDeleted());
    }

    @Test
    void testGivenDeleteByCode_WhenCodeNotExists_ThenShouldThrowEntityNotFoundException() {
        final String invalidCode = "invalid_code";
        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(invalidCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> shipmentService.deleteByCode(invalidCode));
    }
}
