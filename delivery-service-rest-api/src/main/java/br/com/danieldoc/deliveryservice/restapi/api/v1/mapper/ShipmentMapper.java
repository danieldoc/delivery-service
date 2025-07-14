package br.com.danieldoc.deliveryservice.restapi.api.v1.mapper;

import br.com.danieldoc.deliveryservice.domain.entity.Address;
import br.com.danieldoc.deliveryservice.domain.entity.Customer;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.AddressResponse;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.CustomerResponse;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.ShipmentResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ShipmentMapper {

    public ShipmentResponse toResponse(Shipment shipment) {
        if (Objects.isNull(shipment)) {
            return null;
        }

        return ShipmentResponse.builder()
                .code(shipment.getCode())
                .packageQuantity(shipment.getPackageQuantity())
                .deliveryDeadline(shipment.getDeliveryDeadline())
                .status(shipment.getStatus())
                .trackingCode(shipment.getTrackingCode())
                .deliveredAt(shipment.getDeliveredAt())
                .receiverName(shipment.getReceiverName())
                .canceledAt(shipment.getCanceledAt())
                .cancellationReason(shipment.getCancellationReason())
                .customer(CustomerResponse.builder()
                        .document(shipment.getCustomer().getDocument())
                        .fullName(shipment.getCustomer().getFullName())
                        .email(shipment.getCustomer().getEmail())
                        .cellphone(shipment.getCustomer().getCellphone())
                        .build())
                .address(AddressResponse.builder()
                        .street(shipment.getAddress().getStreet())
                        .number(shipment.getAddress().getNumber())
                        .neighborhood(shipment.getAddress().getNeighborhood())
                        .complement(shipment.getAddress().getComplement())
                        .city(shipment.getAddress().getCity())
                        .state(shipment.getAddress().getState())
                        .zipCode(shipment.getAddress().getZipCode())
                        .referencePoint(shipment.getAddress().getReferencePoint())
                        .build())
                .createdAt(shipment.getCreatedAt())
                .updatedAt(shipment.getUpdatedAt())
                .build();
    }

    public Shipment toShipment(ShipmentRequest shipmentRequest) {
        if (Objects.isNull(shipmentRequest)) {
            return null;
        }

        return Shipment.builder()
                .packageQuantity(shipmentRequest.getPackageQuantity())
                .deliveryDeadline(shipmentRequest.getDeliveryDeadline())
                .customer(Customer.builder()
                        .document(shipmentRequest.getCustomer().getDocument())
                        .fullName(shipmentRequest.getCustomer().getFullName())
                        .email(shipmentRequest.getCustomer().getEmail())
                        .cellphone(shipmentRequest.getCustomer().getCellphone())
                        .build())
                .address(Address.builder()
                        .street(shipmentRequest.getAddress().getStreet())
                        .number(shipmentRequest.getAddress().getNumber())
                        .neighborhood(shipmentRequest.getAddress().getNeighborhood())
                        .complement(shipmentRequest.getAddress().getComplement())
                        .city(shipmentRequest.getAddress().getCity())
                        .state(shipmentRequest.getAddress().getState())
                        .zipCode(shipmentRequest.getAddress().getZipCode())
                        .referencePoint(shipmentRequest.getAddress().getReferencePoint())
                        .build())
                .build();
    }

    public Shipment updateFields(ShipmentRequest shipmentRequest, Shipment shipment) {
        shipment.setPackageQuantity(shipmentRequest.getPackageQuantity());
        shipment.setDeliveryDeadline(shipmentRequest.getDeliveryDeadline());

        shipment.getCustomer().setDocument(shipmentRequest.getCustomer().getDocument());
        shipment.getCustomer().setFullName(shipmentRequest.getCustomer().getFullName());
        shipment.getCustomer().setEmail(shipmentRequest.getCustomer().getEmail());
        shipment.getCustomer().setCellphone(shipmentRequest.getCustomer().getCellphone());

        shipment.getAddress().setStreet(shipmentRequest.getAddress().getStreet());
        shipment.getAddress().setNumber(shipmentRequest.getAddress().getNumber());
        shipment.getAddress().setNeighborhood(shipmentRequest.getAddress().getNeighborhood());
        shipment.getAddress().setComplement(shipmentRequest.getAddress().getComplement());
        shipment.getAddress().setCity(shipmentRequest.getAddress().getCity());
        shipment.getAddress().setState(shipmentRequest.getAddress().getState());
        shipment.getAddress().setZipCode(shipmentRequest.getAddress().getZipCode());
        shipment.getAddress().setReferencePoint(shipmentRequest.getAddress().getReferencePoint());

        return shipment;
    }
}
