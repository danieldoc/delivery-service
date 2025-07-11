package br.com.danieldoc.deliveryservice.restapi.api.v1.response;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentResponse {

    private String code;
    private Integer packageQuantity;
    private OffsetDateTime deliveryDeadline;
    private CustomerResponse customer;
    private AddressResponse address;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
