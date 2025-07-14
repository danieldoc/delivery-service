package br.com.danieldoc.deliveryservice.repository;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.entity.ShipmentStatusHistory;
import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import br.com.danieldoc.deliveryservice.repository.config.TestConfig;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import br.com.danieldoc.deliveryservice.repository.fixture.ShipmentFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest(
        properties = {
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.jpa.show-sql=true",
                "spring.jpa.properties.hibernate.format_sql=true"
        })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = TestConfig.class)
class ShipmentStatusHistoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;

    @Test
    void testGivenFindAllByShipmentCode_WhenShipmentHasHistory_ThenShouldReturnHistoryDTOList() {
        Shipment shipment = createAndPersistTestShipment();
        createAndPersistHistory(shipment, ShipmentStatus.CREATED);
        createAndPersistHistory(shipment, ShipmentStatus.PICKED_UP);

        List<ShipmentStatusHistoryDTO> result = shipmentStatusHistoryRepository.findAllByShipmentCode(shipment.getCode());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(ShipmentStatus.CREATED, result.get(0).status());
        Assertions.assertEquals(ShipmentStatus.PICKED_UP, result.get(1).status());
    }

    @Test
    void testGivenFindAllByShipmentCode_WhenShipmentHasNoHistory_ThenShouldReturnEmptyList() {
        Shipment shipment = createAndPersistTestShipment();

        List<ShipmentStatusHistoryDTO> result = shipmentStatusHistoryRepository.findAllByShipmentCode(shipment.getCode());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGivenFindAllByShipmentCode_WhenCodeDoesNotExist_ThenShouldReturnEmptyList() {
        String nonExistentCode = "non-existent-code";

        List<ShipmentStatusHistoryDTO> result = shipmentStatusHistoryRepository.findAllByShipmentCode(nonExistentCode);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    private Shipment createAndPersistTestShipment() {
        Shipment shipment = ShipmentFixture.createTestShipment();
        return entityManager.persistAndFlush(shipment);
    }

    private void createAndPersistHistory(Shipment shipment, ShipmentStatus status) {
        ShipmentStatusHistory history = ShipmentStatusHistory.builder()
                .shipment(shipment)
                .status(status)
                .build();
        entityManager.persistAndFlush(history);
    }
}