package br.com.danieldoc.deliveryservice.restapi.api.v1;

import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentCancellationRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentDeliveryRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentPickupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.Common.NOT_FOUND_RESPONSE_EXAMPLE;
import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.Common.SERVER_ERROR_RESPONSE_EXAMPLE;
import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.ShipmentStatusApi.*;

@Tag(name = "Shipment Status")
@RequestMapping("/v1/shipments/{shipmentCode}")
public interface ShipmentStatusApi {

    @Operation(summary = "Find Shipment Status History")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status history found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = LIST_SHIPMENT_STATUS_HISTORY_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @GetMapping("/status-history")
    List<ShipmentStatusHistoryDTO> findAllStatusHistory(
            @Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
            @NotBlank @PathVariable String shipmentCode);

    @Operation(summary = "Pickup Shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/pickup")
    void pickup(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                @NotBlank @PathVariable String shipmentCode,

                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Shipment receiver information", required = true,
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(example = SHIPMENT_PICKUP_REQUEST_EXAMPLE)))
                @Valid @RequestBody ShipmentPickupRequest shipmentPickUpRequest);

    @Operation(summary = "Deliver Shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/delivery")
    void deliver(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                 @NotBlank @PathVariable String shipmentCode,

                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                         description = "Shipment receiver information", required = true,
                         content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                 schema = @Schema(example = SHIPMENT_DELIVERY_REQUEST_EXAMPLE)))
                 @Valid @RequestBody ShipmentDeliveryRequest shipmentDeliveryRequest);

    @Operation(summary = "Cancel Shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/cancel")
    void cancel(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                @NotBlank @PathVariable String shipmentCode,

                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Shipment receiver information", required = true,
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(example = SHIPMENT_CANCELLATION_REQUEST_EXAMPLE)))
                @Valid @RequestBody ShipmentCancellationRequest shipmentRequest);
}
