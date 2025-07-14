package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.constraints.NotBlank;

public record ShipmentPickupRequest(@NotBlank String trackingCode) {
}
