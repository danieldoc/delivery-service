package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.event.ShipmentStatusUpdatedEvent;
import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.exception.DeliveryServiceBusinessException;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                               ApplicationEventPublisher eventPublisher) {
        this.shipmentRepository = shipmentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Shipment getDetail(String code) {
        log.info("Start find shipment by code={}", code);

        final Shipment shipment = shipmentRepository.findByCodeAndNotDeleted(code)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entrega com o código %s não encontrada", code))
                );

        log.info("End find shipment by code={}", code);
        return shipment;
    }

    @Transactional
    @Override
    public Shipment save(Shipment shipment) {
        log.info("Start save shipment");
        final boolean isCreation = Objects.isNull(shipment.getId());

        if (shipment.cantBeUpdatedOrDeleted()) {
            throw new DeliveryServiceBusinessException(
                    "Entrega não pode ser atualizada pois seu status é: " + shipment.getStatus().getDescription()
            );
        }

        shipment = shipmentRepository.save(shipment);

        if (isCreation) {
            log.info("Shipment created with id={}", shipment.getId());
            eventPublisher.publishEvent(new ShipmentStatusUpdatedEvent(shipment));
        }

        log.info("End save shipment with id={}", shipment.getId());
        return shipment;
    }

    @Transactional
    @Override
    public void deleteByCode(String code) {
        log.info("Start delete shipment by code={}", code);

        final Shipment shipment = getDetail(code);
        if (shipment.cantBeUpdatedOrDeleted()) {
            throw new DeliveryServiceBusinessException(
                    "Entrega não pode ser excluída pois seu status é: " + shipment.getStatus().getDescription()
            );
        }

        shipment.deleteIt();
        shipmentRepository.save(shipment);

        log.info("End delete shipment by code={}", code);
    }

    @Override
    public void existsByCodeOrFail(String shipmentCode) {
        log.info("Checking if shipment with code={} exists", shipmentCode);

        if (!shipmentRepository.existsByCodeAndNotDeleted(shipmentCode)) {
            throw new EntityNotFoundException(String.format("Entrega com o código %s não encontrada", shipmentCode));
        }

        log.info("Shipment with code={} exists", shipmentCode);
    }
}
