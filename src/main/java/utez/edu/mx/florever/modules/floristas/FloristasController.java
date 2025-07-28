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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.utils.APIResponse;

@RestController
@RequestMapping("/api/floristas")
@Tag(name = "Controlador de Floristas", description = "Operaciones relacionadas con floristas")
@SecurityRequirement(name = "bearerAuth")
public class FloristasController {
    @Autowired
    private FloristasService floristasService;

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
    @Operation(summary = "Registrar Florista", description = "Agrega un nuevo florista al sistema")
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
                    responseCode = "500",
                    description = "Error interno de server - No se pudo crear el florista.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> save(@RequestBody @Valid Floristas payload) {
        APIResponse response = floristasService.save(payload);
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
    public ResponseEntity<APIResponse> update(@RequestBody @Valid Floristas payload) {
        APIResponse response = floristasService.update(payload);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("")
    @Operation(summary = "Eliminar Florista", description = "Elimina un florista")
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
    public ResponseEntity<APIResponse> delete(@RequestBody Floristas payload) {
        APIResponse response = floristasService.remove(payload);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // Endpoints adicionales específicos para floristas

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar Florista por Correo", description = "Busca un florista por su correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Florista encontrado por correo",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró florista con ese correo",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findByCorreo(@PathVariable("correo") String correo) {
        APIResponse response = floristasService.findByCorreo(correo);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtrar Floristas por Estado", description = "Obtiene floristas filtrados por estado (activo/inactivo)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Floristas filtrados por estado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findByEstado(@PathVariable("estado") String estado) {
        APIResponse response = floristasService.findByEstado(estado);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/activos")
    @Operation(summary = "Traer Floristas Activos", description = "Obtiene solo los floristas con estado activo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de floristas activos",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> findActiveFloristas() {
        APIResponse response = floristasService.findActiveFloristas();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}/estado/{nuevoEstado}")
    @Operation(summary = "Cambiar Estado de Florista", description = "Cambia el estado de un florista específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado del florista cambiado exitosamente",
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
            )
    })
    public ResponseEntity<APIResponse> cambiarEstado(@PathVariable("id") Long id, @PathVariable("nuevoEstado") String nuevoEstado) {
        APIResponse response = floristasService.cambiarEstado(id, nuevoEstado);
        return new ResponseEntity<>(response, response.getStatus());
    }
}