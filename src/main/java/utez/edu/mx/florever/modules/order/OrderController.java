package utez.edu.mx.florever.modules.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.modules.order.dto.DtoOrderPayload;
import utez.edu.mx.florever.utils.APIResponse;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Controlador de Ordenes", description = "Operaciones relacionadas con ordenes")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    @Operation(summary = "Registrar Orden", description = "Registra una nueva orden en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Orden registrada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo crear la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> save (@RequestBody DtoOrderPayload payload, HttpServletRequest req) {
        APIResponse response = orderService.save(payload, req);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar Órdenes por Estado", description = "Obtiene todas las órdenes que se encuentran en un estado específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de órdenes por estado obtenida con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron órdenes con el estado especificado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> allStatus (@PathVariable String status, HttpServletRequest req) {
        APIResponse response = orderService.findAllByStatus(status , req);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/assign/{id}")
    @Operation(summary = "Asignar Orden", description = "Asigna una orden a un usuario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden asignada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la orden a asignar",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo asignar la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> update( @PathVariable Long id,HttpServletRequest req) {
        APIResponse response = orderService.assign(id, req);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/closed/{id}")
    @Operation(summary = "Cerrar Orden", description = "Marca una orden como completada/cerrada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden cerrada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la orden a cerrar",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo cerrar la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> closed( @PathVariable Long id,HttpServletRequest req) {
        APIResponse response = orderService.closed(id, req);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Orden por ID", description = "Obtiene los detalles de una orden específica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden encontrada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la orden solicitada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo obtener la orden",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findById(@PathVariable Long id) {
        APIResponse response = orderService.get(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/user/history")
    public ResponseEntity getOrderHistory(HttpServletRequest req) {
        APIResponse response = orderService.getOrdersByUserId(req);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
