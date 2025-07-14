package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.constraints.NotBlank;

public record ShipmentDeliveryRequest(@NotBlank String receiverName) {

}
