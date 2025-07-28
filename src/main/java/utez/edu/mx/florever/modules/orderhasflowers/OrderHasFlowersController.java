package utez.edu.mx.florever.modules.orderhasflowers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.utils.APIResponse;

@RestController
@RequestMapping("/api/order-flowers")
@Tag(name = "Controlador de Detalles de Orden", description = "Operaciones relacionadas con los detalles de flores en las órdenes")
@SecurityRequirement(name = "bearerAuth")
public class OrderHasFlowersController {

    @Autowired
    private OrderHasFlowersService orderHasFlowersService;

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Obtener Flores de una Orden", description = "Obtiene todas las flores asociadas a una orden específica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de flores en la orden obtenida con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la orden especificada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> getFlowersByOrder(@PathVariable Long orderId) {
        APIResponse response = orderHasFlowersService.findByOrder(orderId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/order/{orderId}/flower/{flowerId}")
    @Operation(summary = "Agregar Flor a Orden", description = "Agrega una flor específica a una orden existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Flor agregada a la orden con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la orden o la flor especificada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo agregar la flor a la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> addFlowerToOrder(
            @PathVariable Long orderId,
            @PathVariable Long flowerId,
            @RequestBody OrderHasFlowers orderHasFlowers) {
        APIResponse response = orderHasFlowersService.addFlowerToOrder(orderId, flowerId, orderHasFlowers);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/order/{orderId}/flower/{flowerId}")
    @Operation(summary = "Actualizar Detalle de Flor en Orden", description = "Actualiza la cantidad o precio de una flor en una orden")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle de flor actualizado con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el detalle de la flor en la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo actualizar el detalle",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> updateFlowerInOrder(
            @PathVariable Long orderId,
            @PathVariable Long flowerId,
            @RequestBody OrderHasFlowers orderHasFlowers) {
        APIResponse response = orderHasFlowersService.updateFlowerInOrder(orderId, flowerId, orderHasFlowers);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/order/{orderId}/flower/{flowerId}")
    @Operation(summary = "Eliminar Flor de Orden", description = "Elimina una flor específica de una orden")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Flor eliminada de la orden con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la flor en la orden especificada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo eliminar la flor de la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> removeFlowerFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long flowerId) {
        APIResponse response = orderHasFlowersService.removeFlowerFromOrder(orderId, flowerId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
