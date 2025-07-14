package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShipmentPickupRequest(@Size(min = 1, max = 50) @NotBlank String trackingCode) {
}
