package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.event.ShipmentStatusUpdatedEvent;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
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
class ShipmentStatusUpdateServiceImplTest {

    @InjectMocks
    private ShipmentStatusUpdateServiceImpl shipmentStatusUpdateService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void testGivenPickUp_WhenShipmentExists_ThenShouldUpdateStatusToPickedUp() {
        String code = "test-code-pickup";
        String trackingCode = "TRACK123";
        Shipment shipment = Shipment.builder().status(ShipmentStatus.CREATED).build();

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(code)).thenReturn(Optional.of(shipment));

        shipmentStatusUpdateService.pickUp(code, trackingCode);

        ArgumentCaptor<Shipment> shipmentCaptor = ArgumentCaptor.forClass(Shipment.class);
        Mockito.verify(shipmentRepository).save(shipmentCaptor.capture());
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(ShipmentStatusUpdatedEvent.class));

        Shipment savedShipment = shipmentCaptor.getValue();
        Assertions.assertEquals(ShipmentStatus.PICKED_UP, savedShipment.getStatus());
        Assertions.assertEquals(trackingCode, savedShipment.getTrackingCode());
    }

    @Test
    void testGivenPickUp_WhenShipmentDoesNotExist_ThenShouldThrowEntityNotFoundException() {
        String code = "non-existing-code";
        String trackingCode = "TRACK123";

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(code)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()
                -> shipmentStatusUpdateService.pickUp(code, trackingCode));

        Mockito.verify(shipmentRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(eventPublisher, Mockito.never()).publishEvent(Mockito.any());
    }

    @Test
    void testGivenDeliver_WhenShipmentExists_ThenShouldUpdateStatusToDelivered() {
        String code = "test-code-deliver";
        String receiverName = "Marina Silva";
        Shipment shipment = Shipment.builder().status(ShipmentStatus.PICKED_UP).build();

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(code)).thenReturn(Optional.of(shipment));

        shipmentStatusUpdateService.deliver(code, receiverName);

        ArgumentCaptor<Shipment> shipmentCaptor = ArgumentCaptor.forClass(Shipment.class);
        Mockito.verify(shipmentRepository).save(shipmentCaptor.capture());
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(ShipmentStatusUpdatedEvent.class));

        Shipment savedShipment = shipmentCaptor.getValue();
        Assertions.assertEquals(ShipmentStatus.DELIVERED, savedShipment.getStatus());
        Assertions.assertEquals(receiverName, savedShipment.getReceiverName());
        Assertions.assertNotNull(savedShipment.getDeliveredAt());
    }

    @Test
    void testGivenDeliver_WhenShipmentDoesNotExist_ThenShouldThrowEntityNotFoundException() {
        String code = "non-existing-code";
        String receiverName = "Marina Silva";

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(code)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()
                -> shipmentStatusUpdateService.deliver(code, receiverName));

        Mockito.verify(shipmentRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(eventPublisher, Mockito.never()).publishEvent(Mockito.any());
    }

    @Test
    void testGivenCancel_WhenShipmentExists_ThenShouldUpdateStatusToCancelled() {
        String code = "test-code-cancel";
        String reason = "Desistiu da compra";
        Shipment shipment = Shipment.builder().status(ShipmentStatus.CREATED).build();

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(code)).thenReturn(Optional.of(shipment));

        shipmentStatusUpdateService.cancel(code, reason);

        ArgumentCaptor<Shipment> shipmentCaptor = ArgumentCaptor.forClass(Shipment.class);
        Mockito.verify(shipmentRepository).save(shipmentCaptor.capture());
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(ShipmentStatusUpdatedEvent.class));

        Shipment savedShipment = shipmentCaptor.getValue();
        Assertions.assertEquals(ShipmentStatus.CANCELLED, savedShipment.getStatus());
        Assertions.assertEquals(reason, savedShipment.getCancellationReason());
        Assertions.assertNotNull(savedShipment.getCanceledAt());
    }

    @Test
    void testGivenCancel_WhenShipmentDoesNotExist_ThenShouldThrowEntityNotFoundException() {
        String code = "non-existing-code";
        String reason = "Desistiu da compra";

        Mockito.when(shipmentRepository.findByCodeAndNotDeleted(code)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()
                -> shipmentStatusUpdateService.cancel(code, reason));

        Mockito.verify(shipmentRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(eventPublisher, Mockito.never()).publishEvent(Mockito.any());
    }
}