package br.com.danieldoc.deliveryservice.repository.fixture;

import br.com.danieldoc.deliveryservice.domain.entity.Address;
import br.com.danieldoc.deliveryservice.domain.entity.Customer;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;

import java.time.OffsetDateTime;

public final class ShipmentFixture {

    private ShipmentFixture() {
        throw new IllegalStateException("Utility class");
    }

    public static Shipment createTestShipment() {
        return Shipment.builder()
                .customer(createTestCustomer())
                .address(createTestAddress())
                .packageQuantity(1)
                .deliveryDeadline(OffsetDateTime.MAX)
                .build();
    }

    public static Customer createTestCustomer() {
        return Customer.builder()
                .fullName("Cliente de Teste")
                .document("12345678900")
                .cellphone("11999998888")
                .email("cliente.teste@example.com")
                .build();
    }

    private static Address createTestAddress() {
        return Address.builder()
                .street("Rua dos Testes")
                .number("123")
                .complement("Apto 42")
                .neighborhood("Bairro da Qualidade")
                .city("São Paulo")
                .state("SP")
                .zipCode("01234-567")
                .referencePoint("Próximo ao Parque do Ibirapuera")
                .build();
    }
}
