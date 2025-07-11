package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ShipmentRequest {

    @Positive
    @NotNull
    private Integer packageQuantity;

    @Future
    @NotNull
    private OffsetDateTime deliveryDeadline;

    @NotNull
    @Valid
    private CustomerRequest customer;

    @NotNull
    @Valid
    private AddressRequest address;
}
