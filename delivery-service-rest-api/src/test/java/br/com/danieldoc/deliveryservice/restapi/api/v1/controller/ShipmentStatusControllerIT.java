package br.com.danieldoc.deliveryservice.restapi.api.v1.controller;

import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusHistoryService;
import br.com.danieldoc.deliveryservice.business.service.ShipmentStatusUpdateService;
import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import br.com.danieldoc.deliveryservice.restapi.exceptionhandler.ErrorType;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShipmentStatusControllerIT {

    public static final String SHIPMENTS_STATUS_API = "/v1/shipments/{code}";

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ShipmentStatusUpdateService shipmentStatusUpdateService;

    @MockitoBean
    private ShipmentStatusHistoryService shipmentStatusHistoryService;

    @Test
    void testGivenGetStatusHistory_WhenCodeExists_ThenReturnHistoryListAndStatusCode200() throws Exception {
        String shipmentCode = "existing-code";
        var historyList = List.of(
                new ShipmentStatusHistoryDTO(ShipmentStatus.CREATED, OffsetDateTime.now()),
                new ShipmentStatusHistoryDTO(ShipmentStatus.PICKED_UP, OffsetDateTime.now().plusHours(1))
        );

        BDDMockito.given(this.shipmentStatusHistoryService.findAllShipmentStatusHistory(shipmentCode))
                .willReturn(historyList);

        mvc.perform(MockMvcRequestBuilders.get(SHIPMENTS_STATUS_API + "/status-history", shipmentCode)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].status").value("CREATED"))
                .andExpect(jsonPath("$[1].status").value("PICKED_UP"));
    }

    @Test
    void testGivenGetStatusHistory_WhenCodeDoesNotExists_ThenReturnStatusCode404() throws Exception {
        String invalidCode = "invalid-code";
        BDDMockito.given(this.shipmentStatusHistoryService.findAllShipmentStatusHistory(invalidCode))
                .willThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode));

        mvc.perform(MockMvcRequestBuilders.get(SHIPMENTS_STATUS_API + "/status-history", invalidCode)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorType.NOT_FOUND.name()))
                .andExpect(jsonPath("$.title").value(ErrorType.NOT_FOUND.getTitle()));
    }

    @Test
    void testGivenPutToPickup_WhenSuccess_ThenReturnStatusCode204() throws Exception {
        String shipmentCode = "existing-code";
        String trackingCode = "TRACK123";

        BDDMockito.doNothing().when(this.shipmentStatusUpdateService).pickUp(shipmentCode, trackingCode);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS_STATUS_API + "/pickup", shipmentCode)
                        .content(this.getPickupRequestJsonBody(trackingCode))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGivenPutToPickup_WhenCodeDoesNotExists_ThenReturnStatusCode404() throws Exception {
        String invalidCode = "invalid-code";
        String trackingCode = "TRACK123";

        BDDMockito.doThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode))
                .when(this.shipmentStatusUpdateService).pickUp(invalidCode, trackingCode);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS_STATUS_API + "/pickup", invalidCode)
                        .content(this.getPickupRequestJsonBody(trackingCode))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGivenPutToDeliver_WhenSuccess_ThenReturnStatusCode204() throws Exception {
        String shipmentCode = "existing-code";
        String receiverName = "John Doe";

        BDDMockito.doNothing().when(this.shipmentStatusUpdateService).deliver(shipmentCode, receiverName);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS_STATUS_API + "/delivery", shipmentCode)
                        .content(this.getDeliverRequestJsonBody(receiverName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGivenPutToDeliver_WhenCodeDoesNotExists_ThenReturnStatusCode404() throws Exception {
        String invalidCode = "invalid-code";
        String receiverName = "John Doe";

        BDDMockito.doThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode))
                .when(this.shipmentStatusUpdateService).deliver(invalidCode, receiverName);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS_STATUS_API + "/delivery", invalidCode)
                        .content(this.getDeliverRequestJsonBody(receiverName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGivenPutToCancel_WhenSuccess_ThenReturnStatusCode204() throws Exception {
        String shipmentCode = "existing-code";
        String reason = "Customer request";

        BDDMockito.doNothing().when(this.shipmentStatusUpdateService).cancel(shipmentCode, reason);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS_STATUS_API + "/cancel", shipmentCode)
                        .content(this.getCancelRequestJsonBody(reason))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGivenPutToCancel_WhenCodeDoesNotExists_ThenReturnStatusCode404() throws Exception {
        String invalidCode = "invalid-code";
        String reason = "Customer request";

        BDDMockito.doThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode))
                .when(this.shipmentStatusUpdateService).cancel(invalidCode, reason);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS_STATUS_API + "/cancel", invalidCode)
                        .content(this.getCancelRequestJsonBody(reason))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String getPickupRequestJsonBody(String trackingCode) {
        return """
                {
                    "trackingCode": "%s"
                }
                """.formatted(trackingCode);
    }

    private String getDeliverRequestJsonBody(String receiverName) {
        return """
                {
                    "receiverName": "%s"
                }
                """.formatted(receiverName);
    }

    private String getCancelRequestJsonBody(String reason) {
        return """
                {
                    "reason": "%s"
                }
                """.formatted(reason);
    }
}
