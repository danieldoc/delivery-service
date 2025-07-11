package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment getDetail(String code) {
        LOGGER.info("Start find shipment by code={}", code);

        final Shipment shipment = shipmentRepository.findByCodeAndNotDeleted(code)
                .orElseThrow(EntityNotFoundException::new);

        LOGGER.info("End find shipment by code={}", code);
        return shipment;
    }

    @Transactional
    @Override
    public Shipment save(Shipment shipment) {
        LOGGER.info("Start save shipment");

        shipment = shipmentRepository.save(shipment);

        LOGGER.info("End save shipment with id={}", shipment.getId());
        return shipment;
    }

    @Transactional
    @Override
    public void deleteByCode(String code) {
        LOGGER.info("Start delete shipment by code={}", code);

        final Shipment shipment = getDetail(code);

        shipment.deleteIt();
        shipmentRepository.save(shipment);

        LOGGER.info("End delete shipment by code={}", code);
    }
}
