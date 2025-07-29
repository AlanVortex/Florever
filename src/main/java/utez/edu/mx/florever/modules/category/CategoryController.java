package utez.edu.mx.florever.modules.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.utils.APIResponse;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.utils.APIResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private  CategoryService service;

    @GetMapping
    public ResponseEntity getAll() {
        APIResponse response = service.findAll();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Category category) {
        APIResponse response = service.saveA(category);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        APIResponse response = service.findByIdA(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> update(@PathVariable Long id, @RequestBody Category category) {
        APIResponse response = service.update(id,category);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        APIResponse response = service.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/types")
    public ResponseEntity<APIResponse> getAllTypeCategory() {
        APIResponse response = service.categoryTypeList();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{typeCategory}/all")
    public ResponseEntity getByTypeCategory(@PathVariable String typeCategory) {
        APIResponse response = service.findByTypeCategory(typeCategory);
        return new ResponseEntity<>(response, response.getStatus());
    }
}