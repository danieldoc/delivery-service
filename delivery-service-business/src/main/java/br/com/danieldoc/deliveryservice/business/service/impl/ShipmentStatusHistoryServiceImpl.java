package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusHistoryService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.entity.ShipmentStatusHistory;
import br.com.danieldoc.deliveryservice.repository.ShipmentStatusHistoryRepository;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShipmentStatusHistoryServiceImpl implements ShipmentStatusHistoryService {

    private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
    private final ShipmentService shipmentService;

    public ShipmentStatusHistoryServiceImpl(ShipmentStatusHistoryRepository shipmentStatusHistoryRepository,
                                            ShipmentService shipmentService) {
        this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
        this.shipmentService = shipmentService;
    }

    @Transactional
    @Override
    public void createHistory(Shipment shipment) {
        log.info("Creating shipment status history for shipment with code={} and status={}", shipment.getCode(), shipment.getStatus());

        shipmentStatusHistoryRepository.save(
                ShipmentStatusHistory.builder()
                        .status(shipment.getStatus())
                        .shipment(shipment)
                        .build()
        );

        log.info("Shipment status history created successfully for shipment with code={} and status={}", shipment.getCode(), shipment.getStatus());
    }

    @Override
    public List<ShipmentStatusHistoryDTO> findAllShipmentStatusHistory(String shipmentCode) {
        log.info("Retrieving status history for shipment with code={}", shipmentCode);

        shipmentService.existsByCodeOrFail(shipmentCode);
        List<ShipmentStatusHistoryDTO> statusHistories = shipmentStatusHistoryRepository.findAllByShipmentCode(shipmentCode);

        log.info("Found {} status history records for shipment with code={}", statusHistories.size(), shipmentCode);
        return statusHistories;
    }
}
