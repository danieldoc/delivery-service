package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment getDetail(String code) {
        log.info("Start find shipment by code={}", code);

        final Shipment shipment = shipmentRepository.findByCodeAndNotDeleted(code)
                .orElseThrow(EntityNotFoundException::new);

        log.info("End find shipment by code={}", code);
        return shipment;
    }

    @Transactional
    @Override
    public Shipment save(Shipment shipment) {
        log.info("Start save shipment");

        shipment = shipmentRepository.save(shipment);

        log.info("End save shipment with id={}", shipment.getId());
        return shipment;
    }

    @Transactional
    @Override
    public void deleteByCode(String code) {
        log.info("Start delete shipment by code={}", code);

        final Shipment shipment = getDetail(code);

        shipment.deleteIt();
        shipmentRepository.save(shipment);

        log.info("End delete shipment by code={}", code);
    }
}
