package utez.edu.mx.florever.modules.category;

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
@RequestMapping("/api/category")
@Tag(name = "Controlador de Categorías", description = "Operaciones relacionadas con categorías")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private  CategoryService service;

    
    @GetMapping
    @Operation(summary = "Traer Categorías", description = "Trae todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorías obtenida con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity getAll() {
        APIResponse response = service.findAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping
    @Operation(summary = "Registrar Categoría", description = "Agrega una nueva categoría al sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoría registrada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo crear la categoría",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity create(@RequestBody Category category) {
        APIResponse response = service.saveA(category);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer Categoría por ID", description = "Trae una categoría específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría encontrada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la categoría solicitada",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity getById(@PathVariable Long id) {
        APIResponse response = service.findByIdA(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Categoría", description = "Actualiza los datos de una categoría existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría actualizada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la categoría a actualizar",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo actualizar la categoría",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> update(@PathVariable Long id, @RequestBody Category category) {
        APIResponse response = service.update(id,category);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Categoría", description = "Elimina una categoría del sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría eliminada con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la categoría a eliminar",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor - No se pudo eliminar la categoría",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity delete(@PathVariable Long id) {
        APIResponse response = service.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/types")
    @Operation(summary = "Obtener Tipos de Categorías", description = "Obtiene todos los tipos de categorías disponibles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tipos de categorías obtenida con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity<APIResponse> getAllTypeCategory() {
        APIResponse response = service.categoryTypeList();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{typeCategory}/all")
    @Operation(summary = "Obtener Categorías por Tipo", description = "Obtiene todas las categorías de un tipo específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorías por tipo obtenida con éxito",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron categorías del tipo especificado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = APIResponse.class))
                    }
            )
    })
    public ResponseEntity getByTypeCategory(@PathVariable String typeCategory) {
        APIResponse response = service.findByTypeCategory(typeCategory);
        return new ResponseEntity<>(response, response.getStatus());
    }
}