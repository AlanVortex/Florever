package utez.edu.mx.florever.modules.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.utils.APIResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Controlador de Categorías", description = "Operaciones relacionadas con categorías")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

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
    public List<Category> getAll() {
        return service.findAll();
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
    public Category createCategory(@RequestBody Category category) {
        return service.save(category);
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
    public Category getById(@PathVariable Long id) {
        return service.findById(id);
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
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return service.update(id, category);
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
    public void delete(@PathVariable Long id) {
        service.delete(id);
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
    public List<String> getAllTypeCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getTypeCategory)
                .distinct()
                .collect(Collectors.toList());
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
    public List<Category> getByTypeCategory(@PathVariable String typeCategory) {
        return service.findByTypeCategory(typeCategory);
    }
}
