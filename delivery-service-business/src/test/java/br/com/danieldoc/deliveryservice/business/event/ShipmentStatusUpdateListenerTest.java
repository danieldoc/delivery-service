package br.com.danieldoc.deliveryservice.business.event;

import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusHistoryService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShipmentStatusUpdateListenerTest {

    @InjectMocks
    private ShipmentStatusUpdateListener shipmentStatusUpdateListener;

    @Mock
    private ShipmentStatusHistoryService shipmentStatusHistoryService;

    @Test
    void testGivenHandleShipmentStatusUpdate_WhenEventIsReceived_ThenShouldCallCreateHistoryService() {
        Shipment shipment = Shipment.builder()
                .code("test-code")
                .build();
        ShipmentStatusUpdatedEvent event = new ShipmentStatusUpdatedEvent(shipment);

        shipmentStatusUpdateListener.handleShipmentStatusUpdate(event);

        Mockito.verify(shipmentStatusHistoryService, Mockito.times(1)).createHistory(shipment);
    }
}