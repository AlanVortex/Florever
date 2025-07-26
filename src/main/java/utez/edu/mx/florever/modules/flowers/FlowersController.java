package utez.edu.mx.florever.modules.flowers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.utils.APIResponse;

@RestController
@RequestMapping("/api/flowers")
@Tag(name = "Controlador de Flores", description = "Operaciones relacionadas con flores")
@SecurityRequirement(name = "bearerAuth")
public class FlowersController {
    @Autowired
    private FlowersService flowersService;

    @GetMapping("")
    @Operation(summary = "Traer Flores", description = "Trae todas las flores")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Traer todas las flores",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findAll() {
        APIResponse response = flowersService.findAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer Flor en Específico", description = "Trae una flor en específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Traer los datos de cada flor",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro la flor solicitada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo consultar la flor.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findById(@PathVariable("id") Long id) {
        APIResponse response = flowersService.findById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("")
    @Operation(summary = "Registrar Flor", description = "Agrega una nueva flor al sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro de flor completo",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo crear la flor.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> save(@RequestBody @Valid Flowers payload) {
        APIResponse response = flowersService.save(payload);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("")
    @Operation(summary = "Actualizar Flor", description = "Actualiza un dato del registro de una flor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Actualización de flor completa",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro la flor solicitada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo actualizar la flor.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> update(@RequestBody @Valid Flowers payload) {
        APIResponse response = flowersService.update(payload);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("")
    @Operation(summary = "Eliminar Flor", description = "Elimina una flor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Eliminación de flor completa",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro la flor solicitada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo eliminar la flor.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> delete(@RequestBody Flowers payload) {
        APIResponse response = flowersService.remove(payload);
        return new ResponseEntity<>(response, response.getStatus());
    }
}