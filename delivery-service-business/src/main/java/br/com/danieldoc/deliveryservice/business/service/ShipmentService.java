package br.com.danieldoc.deliveryservice.business.service;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShipmentService {

    Shipment getDetail(@NotNull String code);

    Shipment save(@NotNull @Valid Shipment shipment);

    void deleteByCode(@NotBlank String code);

    void existsByCodeOrFail(@NotBlank String shipmentCode);
}
