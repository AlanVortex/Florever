package utez.edu.mx.florever.modules.category;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() { return repository.findAll(); }
    public Category save(Category category) { return repository.save(category); }
    public void delete(Long id) { repository.deleteById(id); }
    public Category findById(Long id) { return repository.findById(id).orElse(null); }
}
