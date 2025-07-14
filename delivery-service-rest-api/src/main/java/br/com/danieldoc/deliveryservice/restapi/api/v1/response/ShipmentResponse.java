package br.com.danieldoc.deliveryservice.restapi.api.v1.response;

import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
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
    private ShipmentStatus status;
    private OffsetDateTime deliveryDeadline;
    private String trackingCode;
    private OffsetDateTime deliveredAt;
    private String receiverName;
    private OffsetDateTime cancelledAt;
    private String cancellationReason;
    private CustomerResponse customer;
    private AddressResponse address;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
