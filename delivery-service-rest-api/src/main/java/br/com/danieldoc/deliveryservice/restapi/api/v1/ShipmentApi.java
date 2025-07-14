package br.com.danieldoc.deliveryservice.restapi.api.v1;

import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.ShipmentResponse;
import br.com.danieldoc.deliveryservice.restapi.exceptionhandler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.Common.NOT_FOUND_RESPONSE_EXAMPLE;
import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.Common.SERVER_ERROR_RESPONSE_EXAMPLE;
import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.ShipmentApi.SHIPMENT_REQUEST_EXAMPLE;
import static br.com.danieldoc.deliveryservice.restapi.api.v1.util.ConstOpenApiResponse.ShipmentApi.SHIPMENT_RESPONSE_EXAMPLE;

@Tag(name = "Shipment")
@RequestMapping("/v1/shipments")
public interface ShipmentApi {

    @Operation(summary = "Get a shipment by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SHIPMENT_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @GetMapping("/{code}")
    ShipmentResponse getDetail(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff") @NotBlank @PathVariable String code);

    @Operation(summary = "Create a new shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SHIPMENT_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Validation error occurred", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<ShipmentResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Shipment to create", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShipmentRequest.class),
                            examples = @ExampleObject(SHIPMENT_REQUEST_EXAMPLE)))
            @Valid @RequestBody ShipmentRequest shipmentRequest);

    @Operation(summary = "Update a shipment by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SHIPMENT_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Validation error occurred", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @PutMapping("/{code}")
    ShipmentResponse update(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                            @NotBlank @PathVariable String code,

                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Shipment to update", required = true,
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(example = SHIPMENT_REQUEST_EXAMPLE)))
                            @Valid @RequestBody ShipmentRequest shipmentRequest);

    @Operation(summary = "Delete a shipment by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    void delete(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                @NotBlank @PathVariable String code);
}
