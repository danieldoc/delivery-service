package br.com.danieldoc.deliveryservice.business.fixture;

import br.com.danieldoc.deliveryservice.domain.entity.Address;
import br.com.danieldoc.deliveryservice.domain.entity.Customer;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;

import java.time.OffsetDateTime;

public final class ShipmentFixture {

    private ShipmentFixture() {
        throw new IllegalStateException("Utility class");
    }

    public static Shipment createTestShipment(String code) {
        return Shipment.builder()
                .id(1L)
                .code(code)
                .customer(createTestCustomer())
                .address(createTestAddress())
                .packageQuantity(1)
                .deliveryDeadline(OffsetDateTime.MAX)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .deleted(false)
                .status(ShipmentStatus.CREATED)
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
