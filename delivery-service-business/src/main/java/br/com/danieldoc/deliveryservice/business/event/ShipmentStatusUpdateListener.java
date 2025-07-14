package br.com.danieldoc.deliveryservice.business.event;

import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ShipmentStatusUpdateListener {

    private final ShipmentStatusHistoryService shipmentStatusHistoryService;

    public ShipmentStatusUpdateListener(ShipmentStatusHistoryService shipmentStatusHistoryService) {
        this.shipmentStatusHistoryService = shipmentStatusHistoryService;
    }

    @Async
    @TransactionalEventListener
    public void handleShipmentStatusUpdate(ShipmentStatusUpdatedEvent event) {
        log.info("Handling shipment status update for shipment with code={}", event.shipment().getCode());

        shipmentStatusHistoryService.createHistory(event.shipment());

        log.info("Shipment status update handled successfully for shipment with code={}", event.shipment().getCode());
    }
}
