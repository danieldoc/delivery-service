package br.com.danieldoc.deliveryservice.restapi.api.v1.controller;

import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusHistoryService;
import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusUpdateService;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import br.com.danieldoc.deliveryservice.restapi.api.v1.ShipmentStatusApi;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentCancellationRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentDeliveryRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentPickupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ShipmentStatusController implements ShipmentStatusApi {

    private final ShipmentStatusUpdateService shipmentStatusUpdateService;
    private final ShipmentStatusHistoryService shipmentStatusHistoryService;

    public ShipmentStatusController(ShipmentStatusUpdateService shipmentStatusUpdateService,
                                    ShipmentStatusHistoryService shipmentStatusHistoryService) {
        this.shipmentStatusUpdateService = shipmentStatusUpdateService;
        this.shipmentStatusHistoryService = shipmentStatusHistoryService;
    }

    @Override
    public List<ShipmentStatusHistoryDTO> findAllStatusHistory(String shipmentCode) {
        return shipmentStatusHistoryService.findAllShipmentStatusHistory(shipmentCode);
    }

    @Override
    public void pickup(String shipmentCode, ShipmentPickupRequest shipmentPickUpRequest) {
        shipmentStatusUpdateService.pickUp(shipmentCode, shipmentPickUpRequest.trackingCode());
    }

    @Override
    public void deliver(String shipmentCode, ShipmentDeliveryRequest shipmentDeliveryRequest) {
        shipmentStatusUpdateService.deliver(shipmentCode, shipmentDeliveryRequest.receiverName());
    }

    @Override
    public void cancel(String shipmentCode, ShipmentCancellationRequest shipmentRequest) {
        shipmentStatusUpdateService.cancel(shipmentCode, shipmentRequest.reason());
    }
}
