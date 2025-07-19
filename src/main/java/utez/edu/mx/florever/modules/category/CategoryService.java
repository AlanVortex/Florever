package utez.edu.mx.florever.modules.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() { return repository.findAll(); }
    public Category save(Category category) { return repository.save(category); }
    public void delete(Long id) { repository.deleteById(id); }
    public Category findById(Long id) { return repository.findById(id).orElse(null); }

    public List<Category> findByTypeCategory(String typeCategory) {
        return categoryRepository.findByTypeCategory(typeCategory);
    }

    public Category update(Long id, Category updatedCategory) {
        Category existing = findById(id);
        if (updatedCategory.getName() != null) {
            existing.setName(updatedCategory.getName());
        }
        if (updatedCategory.getDescription() != null) {
            existing.setDescription(updatedCategory.getDescription());
        }
        if (updatedCategory.getPrice() != 0.0) {
            existing.setPrice(updatedCategory.getPrice());
        }
        if (updatedCategory.getTotalQuantityFlowers() != 0) {
            existing.setTotalQuantityFlowers(updatedCategory.getTotalQuantityFlowers());
        }
        if (updatedCategory.getTypeCategory() != null) {
            existing.setTypeCategory(updatedCategory.getTypeCategory());
        }
        return save(existing);
    }
}
