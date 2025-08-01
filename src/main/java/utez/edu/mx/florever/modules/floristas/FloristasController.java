package utez.edu.mx.florever.modules.floristas;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.utils.APIResponse;

@RestController
@RequestMapping("/api/floristas")
@Tag(name = "Controlador de Floristas", description = "Operaciones relacionadas con floristas")
@SecurityRequirement(name = "bearerAuth")
public class FloristasController {
    @Autowired
    private FloristasService floristasService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("")
    @Operation(summary = "Traer Floristas", description = "Trae todos los floristas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Traer todos los floristas",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findAll() {
        APIResponse response = floristasService.findAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer Florista en Específico", description = "Trae un florista en específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Traer los datos de cada florista",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el florista solicitado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo consultar el florista.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findById(@PathVariable("id") Long id) {
        APIResponse response = floristasService.findById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("")
    @Operation(summary = "Registrar Florista", description = "Agrega un nuevo florista al sistema (Solo Admin)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro de florista completo",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permisos para crear floristas",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo crear el florista.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> save(@RequestBody @Valid BeanUser payload, HttpServletRequest request) {
        APIResponse response = floristasService.save(payload, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("")
    @Operation(summary = "Actualizar Florista", description = "Actualiza un dato del registro de un florista")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Actualización de florista completa",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permisos para actualizar este florista",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el florista solicitado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo actualizar el florista.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> update(@RequestBody @Valid BeanUser payload, HttpServletRequest request) {
        APIResponse response = floristasService.update(payload, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Florista", description = "Elimina un florista (Solo Admin)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Eliminación de florista completa",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permisos para eliminar floristas",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el florista solicitado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno de server - No se pudo eliminar el florista.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        APIResponse response = floristasService.remove(id, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Cambiar estado de florista", description = "Activa o desactiva un florista")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado actualizado correctamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Florista no encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permisos para actualizar el estado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> toggleStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") boolean status,
            HttpServletRequest request) {

        APIResponse response = floristasService.updateStatus(id, status, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

}