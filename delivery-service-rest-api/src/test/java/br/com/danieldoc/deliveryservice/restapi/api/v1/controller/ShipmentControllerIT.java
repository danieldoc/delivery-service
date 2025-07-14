package br.com.danieldoc.deliveryservice.restapi.api.v1.controller;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import br.com.danieldoc.deliveryservice.restapi.api.v1.fixture.ShipmentFixture;
import br.com.danieldoc.deliveryservice.restapi.exceptionhandler.ErrorType;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShipmentControllerIT {

    public static final String SHIPMENTS = "/v1/shipments";

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ShipmentService shipmentService;

    @Test
    void testGivenPostToCreate_WhenSuccess_ThenReturnANewShipmentWithStatusCode201AndHeaderLocation() throws Exception {
        Shipment shipment = ShipmentFixture.createTestShipment();

        BDDMockito.given(this.shipmentService.save(Mockito.any(Shipment.class))).willReturn(shipment);

        mvc.perform(MockMvcRequestBuilders.post(SHIPMENTS)
                        .content(this.getShipmentRequestJsonBody())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(jsonPath("$.code").value(shipment.getCode()))
                .andExpect(jsonPath("$.packageQuantity").value(shipment.getPackageQuantity()))
                .andExpect(jsonPath("$.deliveryDeadline").value(shipment.getDeliveryDeadline().toString()))
                .andExpect(jsonPath("$.status").value(shipment.getStatus().name()))
                .andExpect(jsonPath("$.trackingCode").value(shipment.getTrackingCode()))
                .andExpect(jsonPath("$.receiverName").value(shipment.getReceiverName()))
                .andExpect(jsonPath("$.deliveredAt").value(shipment.getDeliveredAt()))
                .andExpect(jsonPath("$.cancellationReason").value(shipment.getCancellationReason()))
                .andExpect(jsonPath("$.canceledAt").value(shipment.getCanceledAt()))
                .andExpect(jsonPath("$.customer.document").value(shipment.getCustomer().getDocument()))
                .andExpect(jsonPath("$.customer.fullName").value(shipment.getCustomer().getFullName()))
                .andExpect(jsonPath("$.customer.cellphone").value(shipment.getCustomer().getCellphone()))
                .andExpect(jsonPath("$.customer.email").value(shipment.getCustomer().getEmail()))
                .andExpect(jsonPath("$.address.street").value(shipment.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.number").value(shipment.getAddress().getNumber()))
                .andExpect(jsonPath("$.address.neighborhood").value(shipment.getAddress().getNeighborhood()))
                .andExpect(jsonPath("$.address.complement").isEmpty())
                .andExpect(jsonPath("$.address.city").value(shipment.getAddress().getCity()))
                .andExpect(jsonPath("$.address.state").value(shipment.getAddress().getState()))
                .andExpect(jsonPath("$.address.zipCode").value(shipment.getAddress().getZipCode()))
                .andExpect(jsonPath("$.address.referencePoint").isEmpty())
                .andExpect(jsonPath("$.createdAt").value(shipment.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(shipment.getUpdatedAt().toString()))
        ;
    }

    @Test
    void testGivenPuToUpdate_WhenSuccess_ThenReturnAUpdatedShipmentWithStatusCode200AndAShipmentResponse() throws Exception {
        Shipment shipment = ShipmentFixture.createTestShipment();

        BDDMockito.given(this.shipmentService.getDetail(shipment.getCode())).willReturn(shipment);
        BDDMockito.given(this.shipmentService.save(shipment)).willReturn(shipment);

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS + "/{code}", shipment.getCode())
                        .content(this.getShipmentRequestJsonBody())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(shipment.getCode()))
                .andExpect(jsonPath("$.packageQuantity").value(shipment.getPackageQuantity()))
                .andExpect(jsonPath("$.deliveryDeadline").value(shipment.getDeliveryDeadline().toString()))
                .andExpect(jsonPath("$.status").value(shipment.getStatus().name()))
                .andExpect(jsonPath("$.trackingCode").value(shipment.getTrackingCode()))
                .andExpect(jsonPath("$.receiverName").value(shipment.getReceiverName()))
                .andExpect(jsonPath("$.deliveredAt").value(shipment.getDeliveredAt()))
                .andExpect(jsonPath("$.cancellationReason").value(shipment.getCancellationReason()))
                .andExpect(jsonPath("$.canceledAt").value(shipment.getCanceledAt()))
                .andExpect(jsonPath("$.customer.document").value(shipment.getCustomer().getDocument()))
                .andExpect(jsonPath("$.customer.fullName").value(shipment.getCustomer().getFullName()))
                .andExpect(jsonPath("$.customer.cellphone").value(shipment.getCustomer().getCellphone()))
                .andExpect(jsonPath("$.customer.email").value(shipment.getCustomer().getEmail()))
                .andExpect(jsonPath("$.address.street").value(shipment.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.number").value(shipment.getAddress().getNumber()))
                .andExpect(jsonPath("$.address.neighborhood").value(shipment.getAddress().getNeighborhood()))
                .andExpect(jsonPath("$.address.complement").isEmpty())
                .andExpect(jsonPath("$.address.city").value(shipment.getAddress().getCity()))
                .andExpect(jsonPath("$.address.state").value(shipment.getAddress().getState()))
                .andExpect(jsonPath("$.address.zipCode").value(shipment.getAddress().getZipCode()))
                .andExpect(jsonPath("$.address.referencePoint").isEmpty())
                .andExpect(jsonPath("$.createdAt").value(shipment.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(shipment.getUpdatedAt().toString()));
    }

    @Test
    void testGivenPuToUpdate_WhenCodeDoesNotExists_ThenReturnStatusCode404AndAnErrorResponse() throws Exception {
        String invalidCode = "invalid-code";
        BDDMockito.given(this.shipmentService.getDetail(invalidCode))
                .willThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode));

        mvc.perform(MockMvcRequestBuilders.put(SHIPMENTS + "/{code}", invalidCode)
                        .content(this.getShipmentRequestJsonBody())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorType.NOT_FOUND.name()))
                .andExpect(jsonPath("$.title").value(ErrorType.NOT_FOUND.getTitle()))
                .andExpect(jsonPath("$.description").value("Shipment not found with code: " + invalidCode))
                .andExpect(jsonPath("$.fields").isEmpty());
    }

    @Test
    void testGivenGetToGetDetail_WhenCodeExists_ThenReturnStatusCode200AndAShipmentResponse() throws Exception {
        Shipment shipment = ShipmentFixture.createTestShipment();

        BDDMockito.given(this.shipmentService.getDetail(shipment.getCode())).willReturn(shipment);

        mvc.perform(MockMvcRequestBuilders.get(SHIPMENTS + "/{code}", shipment.getCode())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(shipment.getCode()))
                .andExpect(jsonPath("$.packageQuantity").value(shipment.getPackageQuantity()))
                .andExpect(jsonPath("$.deliveryDeadline").value(shipment.getDeliveryDeadline().toString()))
                .andExpect(jsonPath("$.status").value(shipment.getStatus().name()))
                .andExpect(jsonPath("$.trackingCode").value(shipment.getTrackingCode()))
                .andExpect(jsonPath("$.receiverName").value(shipment.getReceiverName()))
                .andExpect(jsonPath("$.deliveredAt").value(shipment.getDeliveredAt()))
                .andExpect(jsonPath("$.cancellationReason").value(shipment.getCancellationReason()))
                .andExpect(jsonPath("$.canceledAt").value(shipment.getCanceledAt()))
                .andExpect(jsonPath("$.customer.document").value(shipment.getCustomer().getDocument()))
                .andExpect(jsonPath("$.customer.fullName").value(shipment.getCustomer().getFullName()))
                .andExpect(jsonPath("$.customer.cellphone").value(shipment.getCustomer().getCellphone()))
                .andExpect(jsonPath("$.customer.email").value(shipment.getCustomer().getEmail()))
                .andExpect(jsonPath("$.address.street").value(shipment.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.number").value(shipment.getAddress().getNumber()))
                .andExpect(jsonPath("$.address.neighborhood").value(shipment.getAddress().getNeighborhood()))
                .andExpect(jsonPath("$.address.complement").isEmpty())
                .andExpect(jsonPath("$.address.city").value(shipment.getAddress().getCity()))
                .andExpect(jsonPath("$.address.state").value(shipment.getAddress().getState()))
                .andExpect(jsonPath("$.address.zipCode").value(shipment.getAddress().getZipCode()))
                .andExpect(jsonPath("$.address.referencePoint").isEmpty())
                .andExpect(jsonPath("$.createdAt").value(shipment.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(shipment.getUpdatedAt().toString()));
    }

    @Test
    void testGivenGetToGetDetail_WhenCodeDoesNotExists_ThenReturnStatusCode404AndAnErrorResponse() throws Exception {
        String invalidCode = "invalid-code";
        BDDMockito.given(this.shipmentService.getDetail(invalidCode))
                .willThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode));

        mvc.perform(MockMvcRequestBuilders.get(SHIPMENTS + "/{code}", invalidCode)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorType.NOT_FOUND.name()))
                .andExpect(jsonPath("$.title").value(ErrorType.NOT_FOUND.getTitle()))
                .andExpect(jsonPath("$.description").value("Shipment not found with code: " + invalidCode))
                .andExpect(jsonPath("$.fields").isEmpty());
    }

    @Test
    void testGivenDeleteShipment_WhenCodeExists_ThenReturnStatusCode204() throws Exception {
        Shipment shipment = ShipmentFixture.createTestShipment();
        BDDMockito.given(this.shipmentService.getDetail(shipment.getCode())).willReturn(shipment);

        mvc.perform(MockMvcRequestBuilders.delete(SHIPMENTS + "/{code}", shipment.getCode()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGivenDeleteShipment_WhenCodeDoesNotExists_ThenReturnStatusCode404AndAnErrorResponse() throws Exception {
        String invalidCode = "invalid-code";
        BDDMockito.willThrow(new EntityNotFoundException("Shipment not found with code: " + invalidCode))
                .given(this.shipmentService).deleteByCode(invalidCode);

        mvc.perform(MockMvcRequestBuilders.delete(SHIPMENTS + "/{code}", invalidCode))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorType.NOT_FOUND.name()))
                .andExpect(jsonPath("$.title").value(ErrorType.NOT_FOUND.getTitle()))
                .andExpect(jsonPath("$.description").value("Shipment not found with code: " + invalidCode))
                .andExpect(jsonPath("$.fields").isEmpty());
    }

    private String getShipmentRequestJsonBody() {
        return """
                {
                     "packageQuantity": 2,
                     "deliveryDeadline": "2025-07-31T23:59:59-03:00",
                     "customer": {
                         "document": "96293583019",
                         "fullName": "João da Silva",
                         "cellphone": "11987654321",
                         "email": "joao.silva@example.com"
                     },
                     "address": {
                         "street": "Avenida Paulista",
                         "number": "1578",
                         "neighborhood": "Bela Vista",
                         "complement": null,
                         "city": "São Paulo",
                         "state": "SP",
                         "zipCode": "01310200",
                         "referencePoint": null
                     }
                 }
                """;
    }
}
