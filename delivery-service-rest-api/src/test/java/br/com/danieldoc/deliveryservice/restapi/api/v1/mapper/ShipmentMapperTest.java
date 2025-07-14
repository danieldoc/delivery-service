package br.com.danieldoc.deliveryservice.restapi.api.v1.mapper;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.restapi.api.v1.fixture.ShipmentFixture;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.ShipmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShipmentMapperTest {

    private ShipmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ShipmentMapper();
    }

    @Test
    void testGivenToResponse_WhenCalled_ThenShipmentResponseShouldBeCreatedBasedOnShipmentFieldValues() {
        Shipment shipment = ShipmentFixture.createTestShipment();

        ShipmentResponse response = mapper.toResponse(shipment);

        assertNotNull(response);
        assertEquals(shipment.getCode(), response.getCode());
        assertEquals(shipment.getPackageQuantity(), response.getPackageQuantity());
        assertEquals(shipment.getDeliveryDeadline(), response.getDeliveryDeadline());
        assertEquals(shipment.getCreatedAt(), response.getCreatedAt());
        assertEquals(shipment.getUpdatedAt(), response.getUpdatedAt());

        assertEquals(shipment.getStatus(), response.getStatus());
        assertEquals(shipment.getTrackingCode(), response.getTrackingCode());
        assertEquals(shipment.getDeliveredAt(), response.getDeliveredAt());
        assertEquals(shipment.getReceiverName(), response.getReceiverName());
        assertEquals(shipment.getCanceledAt(), response.getCanceledAt());
        assertEquals(shipment.getCancellationReason(), response.getCancellationReason());

        // Customer
        assertNotNull(response.getCustomer());
        assertEquals(shipment.getCustomer().getDocument(), response.getCustomer().getDocument());
        assertEquals(shipment.getCustomer().getFullName(), response.getCustomer().getFullName());
        assertEquals(shipment.getCustomer().getEmail(), response.getCustomer().getEmail());
        assertEquals(shipment.getCustomer().getCellphone(), response.getCustomer().getCellphone());

        // Address
        assertNotNull(response.getAddress());
        assertEquals(shipment.getAddress().getStreet(), response.getAddress().getStreet());
        assertEquals(shipment.getAddress().getNumber(), response.getAddress().getNumber());
        assertEquals(shipment.getAddress().getNeighborhood(), response.getAddress().getNeighborhood());
        assertEquals(shipment.getAddress().getComplement(), response.getAddress().getComplement());
        assertEquals(shipment.getAddress().getCity(), response.getAddress().getCity());
        assertEquals(shipment.getAddress().getState(), response.getAddress().getState());
        assertEquals(shipment.getAddress().getPostalCode(), response.getAddress().getPostalCode());
        assertEquals(shipment.getAddress().getReferencePoint(), response.getAddress().getReferencePoint());
    }

    @Test
    void testGivenToShipment_WhenCalled_ThenShipmentShouldBeCreatedBasedOnRequestFieldValues() {
        final ShipmentRequest request = ShipmentFixture.createTestShipmentRequest();

        final Shipment shipment = mapper.toShipment(request);

        assertNotNull(shipment);
        assertEquals(request.getPackageQuantity(), shipment.getPackageQuantity());
        assertEquals(request.getDeliveryDeadline(), shipment.getDeliveryDeadline());

        // Customer
        assertNotNull(shipment.getCustomer());
        assertEquals(request.getCustomer().getDocument(), shipment.getCustomer().getDocument());
        assertEquals(request.getCustomer().getFullName(), shipment.getCustomer().getFullName());
        assertEquals(request.getCustomer().getEmail(), shipment.getCustomer().getEmail());
        assertEquals(request.getCustomer().getCellphone(), shipment.getCustomer().getCellphone());

        // Address
        assertNotNull(shipment.getAddress());
        assertEquals(request.getAddress().getStreet(), shipment.getAddress().getStreet());
        assertEquals(request.getAddress().getNumber(), shipment.getAddress().getNumber());
        assertEquals(request.getAddress().getNeighborhood(), shipment.getAddress().getNeighborhood());
        assertEquals(request.getAddress().getComplement(), shipment.getAddress().getComplement());
        assertEquals(request.getAddress().getCity(), shipment.getAddress().getCity());
        assertEquals(request.getAddress().getState(), shipment.getAddress().getState());
        assertEquals(request.getAddress().getPostalCode(), shipment.getAddress().getPostalCode());
        assertEquals(request.getAddress().getReferencePoint(), shipment.getAddress().getReferencePoint());
    }

    @Test
    void testGivenUpdateFields_WhenCalled_ThenShipmentFieldsShouldBeUpdatedWithRequestValues() {
        Shipment shipment = ShipmentFixture.createTestShipment();
        ShipmentRequest request = ShipmentFixture.createTestShipmentRequest();

        mapper.updateFields(request, shipment);

        assertEquals(request.getPackageQuantity(), shipment.getPackageQuantity());
        assertEquals(request.getDeliveryDeadline(), shipment.getDeliveryDeadline());

        // Customer
        assertEquals(request.getCustomer().getDocument(), shipment.getCustomer().getDocument());
        assertEquals(request.getCustomer().getFullName(), shipment.getCustomer().getFullName());
        assertEquals(request.getCustomer().getEmail(), shipment.getCustomer().getEmail());
        assertEquals(request.getCustomer().getCellphone(), shipment.getCustomer().getCellphone());

        // Address
        assertEquals(request.getAddress().getStreet(), shipment.getAddress().getStreet());
        assertEquals(request.getAddress().getNumber(), shipment.getAddress().getNumber());
        assertEquals(request.getAddress().getNeighborhood(), shipment.getAddress().getNeighborhood());
        assertEquals(request.getAddress().getComplement(), shipment.getAddress().getComplement());
        assertEquals(request.getAddress().getCity(), shipment.getAddress().getCity());
        assertEquals(request.getAddress().getState(), shipment.getAddress().getState());
        assertEquals(request.getAddress().getPostalCode(), shipment.getAddress().getPostalCode());
        assertEquals(request.getAddress().getReferencePoint(), shipment.getAddress().getReferencePoint());
    }
}
