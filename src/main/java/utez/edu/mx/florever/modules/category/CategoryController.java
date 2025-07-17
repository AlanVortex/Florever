package utez.edu.mx.florever.modules.category;


import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAll() { return service.findAll(); }

    @PostMapping
    public Category create(@RequestBody Category category) { return service.save(category); }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        return service.save(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}