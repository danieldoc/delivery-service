package br.com.danieldoc.deliveryservice.business.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShipmentStatusUpdateService {

    void pickUp(@NotBlank String code, @NotBlank String trackingCode);

    void deliver(@NotBlank String code, @NotBlank String receiverName);

    void cancel(@NotBlank String code, @NotBlank String cancellationReason);
}
