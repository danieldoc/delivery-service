package br.com.danieldoc.deliveryservice.restapi.api.v1.fixture;

import br.com.danieldoc.deliveryservice.domain.entity.Address;
import br.com.danieldoc.deliveryservice.domain.entity.Customer;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.AddressRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.CustomerRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public final class ShipmentFixture {

    private ShipmentFixture() {
        throw new IllegalStateException("Utility class");
    }

    public static Shipment createTestShipment() {
        return Shipment.builder()
                .id(1L)
                .code("SHIP-123456")
                .packageQuantity(2)
                .deliveryDeadline(OffsetDateTime.MAX)
                .customer(Customer.builder()
                        .document("12345678900")
                        .fullName("João da Silva")
                        .cellphone(null)
                        .email(null)
                        .build())
                .address(Address.builder()
                        .street("Avenida Paulista")
                        .number("1578")
                        .neighborhood("Bela Vista")
                        .complement(null)
                        .city("São Paulo")
                        .state("SP")
                        .zipCode("01310200")
                        .referencePoint(null)
                        .build())
                .createdAt(OffsetDateTime.parse("2025-07-31T23:59:59Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .updatedAt(OffsetDateTime.parse("2025-07-31T23:59:59Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build();
    }

    public static ShipmentRequest createTestShipmentRequest() {
        ShipmentRequest request = new ShipmentRequest();
        request.setPackageQuantity(5);
        request.setDeliveryDeadline(OffsetDateTime.parse("2025-12-31T23:59:59Z"));

        CustomerRequest customer = new CustomerRequest();
        customer.setDocument("9999999999");
        customer.setFullName("Novo Nome");
        customer.setCellphone("222");
        customer.setEmail("novo@email.com");
        request.setCustomer(customer);

        AddressRequest address = new AddressRequest();
        address.setStreet("Nova Rua");
        address.setNumber("99");
        address.setNeighborhood("Novo Bairro");
        address.setComplement("Apto 99");
        address.setCity("Nova Cidade");
        address.setState("NS");
        address.setZipCode("99999999");
        address.setReferencePoint("Shopping");
        request.setAddress(address);

        return request;
    }
}
