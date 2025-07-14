package br.com.danieldoc.deliveryservice.business.service.impl;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.entity.ShipmentStatusHistory;
import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.ShipmentStatusHistoryRepository;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ShipmentStatusHistoryServiceImplTest {

    @InjectMocks
    private ShipmentStatusHistoryServiceImpl shipmentStatusHistoryService;

    @Mock
    private ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;

    @Mock
    private ShipmentService shipmentService;

    @Test
    void testGivenCreateHistory_WhenCalledWithValidShipment_ThenShouldSaveHistory() {
        Shipment shipment = Shipment.builder()
                .id(1L)
                .code("test-code")
                .status(ShipmentStatus.CREATED)
                .build();

        shipmentStatusHistoryService.createHistory(shipment);

        ArgumentCaptor<ShipmentStatusHistory> captor = ArgumentCaptor.forClass(ShipmentStatusHistory.class);
        Mockito.verify(shipmentStatusHistoryRepository).save(captor.capture());

        ShipmentStatusHistory savedHistory = captor.getValue();
        Assertions.assertNotNull(savedHistory);
        Assertions.assertEquals(shipment, savedHistory.getShipment());
        Assertions.assertEquals(ShipmentStatus.CREATED, savedHistory.getStatus());
    }

    @Test
    void testGivenFindAllShipmentStatusHistory_WhenShipmentExists_ThenShouldReturnHistoryList() {
        String shipmentCode = "existing-code";
        List<ShipmentStatusHistoryDTO> expectedHistory = Collections.singletonList(
                new ShipmentStatusHistoryDTO(ShipmentStatus.CREATED, OffsetDateTime.now())
        );

        Mockito.doNothing().when(shipmentService).existsByCodeOrFail(shipmentCode);
        Mockito.when(shipmentStatusHistoryRepository.findAllByShipmentCode(shipmentCode)).thenReturn(expectedHistory);

        List<ShipmentStatusHistoryDTO> actualHistory = shipmentStatusHistoryService.findAllShipmentStatusHistory(shipmentCode);

        Mockito.verify(shipmentService).existsByCodeOrFail(shipmentCode);
        Mockito.verify(shipmentStatusHistoryRepository).findAllByShipmentCode(shipmentCode);
        Assertions.assertNotNull(actualHistory);
        Assertions.assertEquals(1, actualHistory.size());
        Assertions.assertEquals(expectedHistory, actualHistory);
    }

    @Test
    void testGivenFindAllShipmentStatusHistory_WhenShipmentDoesNotExist_ThenShouldThrowException() {
        String shipmentCode = "non-existing-code";
        Mockito.doThrow(new EntityNotFoundException("Entrega não encontrada"))
                .when(shipmentService).existsByCodeOrFail(shipmentCode);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            shipmentStatusHistoryService.findAllShipmentStatusHistory(shipmentCode);
        });

        Mockito.verify(shipmentService).existsByCodeOrFail(shipmentCode);
        Mockito.verify(shipmentStatusHistoryRepository, Mockito.never()).findAllByShipmentCode(Mockito.anyString());
    }
}