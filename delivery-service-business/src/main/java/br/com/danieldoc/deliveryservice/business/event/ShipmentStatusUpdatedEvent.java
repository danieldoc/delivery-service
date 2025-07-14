package br.com.danieldoc.deliveryservice.business.event;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;

public record ShipmentStatusUpdatedEvent(Shipment shipment) {
}
