package br.com.danieldoc.deliveryservice.repository;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.repository.config.TestConfig;
import br.com.danieldoc.deliveryservice.repository.fixture.ShipmentFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest(
        properties = {
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.jpa.show-sql=true",
                "spring.jpa.properties.hibernate.format_sql=true"
        })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = TestConfig.class)
class ShipmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShipmentRepository shipmentRepository;

    private Shipment shipmentNotDeleted;
    private Shipment shipmentDeleted;

    @BeforeEach
    void setUp() {
        this.shipmentNotDeleted = entityManager.merge(ShipmentFixture.createTestShipment());

        this.shipmentDeleted = entityManager.merge(ShipmentFixture.createTestShipment());
        this.shipmentDeleted.deleteIt();
        this.shipmentDeleted = entityManager.merge(this.shipmentDeleted);

        entityManager.flush();
    }

    @Test
    void testGivenFindByCodeAndNotDeleted_WhenCodeExistsAndDeletedIsFalse_ThenReturnsAShipment() {
        Optional<Shipment> optionalShipment = shipmentRepository.findByCodeAndNotDeleted(shipmentNotDeleted.getCode());
        Assertions.assertTrue(optionalShipment.isPresent());
    }

    @Test
    void testGivenFindByCodeAndNotDeleted_WhenCodeExistsAndDeletedIsTrue_ThenReturnsEmpty() {
        Optional<Shipment> optionalShipment = shipmentRepository.findByCodeAndNotDeleted(shipmentDeleted.getCode());
        Assertions.assertTrue(optionalShipment.isEmpty());
    }

    @Test
    void testGivenFindByCodeAndNotDeleted_WhenFindNonExistentCode_ThenReturnsEmpty() {
        Optional<Shipment> optionalShipment = shipmentRepository.findByCodeAndNotDeleted("non-existent-code");
        Assertions.assertTrue(optionalShipment.isEmpty());
    }
}
