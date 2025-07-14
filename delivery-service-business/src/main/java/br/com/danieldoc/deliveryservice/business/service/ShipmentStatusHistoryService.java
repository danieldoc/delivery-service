package br.com.danieldoc.deliveryservice.business.service;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ShipmentStatusHistoryService {

    void createHistory(Shipment shipment);

    List<ShipmentStatusHistoryDTO> findAllShipmentStatusHistory(@NotBlank String shipmentCode);
}
