package utez.edu.mx.florever.modules.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryService service;


    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return service.save(category);
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return service.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/types")
    public List<String> getAllTypeCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getTypeCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/{typeCategory}/all")
    public List<Category> getByTypeCategory(@PathVariable String typeCategory) {
        return service.findByTypeCategory(typeCategory);
    }


}