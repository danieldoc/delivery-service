package br.com.danieldoc.deliveryservice.repository.dto;

import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;

import java.time.OffsetDateTime;

public record ShipmentStatusHistoryDTO(
        ShipmentStatus status,
        OffsetDateTime createdAt
) {
}
