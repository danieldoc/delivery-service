package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.event.ShipmentStatusUpdatedEvent;
import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusUpdateService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Transactional
@Service
public class ShipmentStatusUpdateServiceImpl implements ShipmentStatusUpdateService {

    private final ShipmentRepository shipmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ShipmentStatusUpdateServiceImpl(ShipmentRepository shipmentRepository,
                                           ApplicationEventPublisher eventPublisher) {
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void pickUp(String code, String trackingCode) {
        updateStatus(code, shipment -> shipment.pickUp(trackingCode));
    }

    @Override
    public void deliver(String code, String receiverName) {
        updateStatus(code, shipment -> shipment.deliver(receiverName));
    }

    @Override
    public void cancel(String code, String cancellationReason) {
        updateStatus(code, shipment -> shipment.cancel(cancellationReason));
    }

    private void updateStatus(String code, Consumer<Shipment> statusChange) {
        log.info("Start shipment by code={}", code);

        Shipment shipment = shipmentRepository.findByCodeAndNotDeleted(code)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entrega com o código %s não encontrada", code)));
        statusChange.accept(shipment);
        shipmentRepository.save(shipment);

        eventPublisher.publishEvent(new ShipmentStatusUpdatedEvent(shipment));

        log.info("End shipment by code={}", code);
    }
}
