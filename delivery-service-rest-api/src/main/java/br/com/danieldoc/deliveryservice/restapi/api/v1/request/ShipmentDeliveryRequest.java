package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShipmentDeliveryRequest(@Size(min = 1, max = 255) @NotBlank String receiverName) {

}
