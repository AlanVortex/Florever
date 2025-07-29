package utez.edu.mx.florever.modules.category;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import utez.edu.mx.florever.utils.APIResponse;
import org.springframework.stereotype.Service;
import utez.edu.mx.florever.utils.APIResponse;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category save(Category category) { return repository.save(category); }
    public APIResponse saveA(Category category) {
        try {
            return new APIResponse("Se a gaurdado la categoria corractemente" , HttpStatus.CREATED ,false , save(category) );
        }
        catch (Exception e) {
            return  new APIResponse<>( HttpStatus.NOT_FOUND, true, "Error al guardar la categoria");
        }
    }

    public APIResponse delete(Long id) {
        try {
            repository.deleteById(id);
            return  new APIResponse<>( HttpStatus.OK, false, "Se a eliminado la categoria");

        }catch (Exception e) {
            return  new APIResponse<>( HttpStatus.NOT_FOUND, true, "Error al Eliminar la categoria");

        }
    }

    public Category findById(Long id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found")); }

    public APIResponse findByTypeCategory(String typeCategory) {
        try {
            return new APIResponse("Se a gaurdado la categoria corractemente" , HttpStatus.OK ,false , categoryRepository.findByTypeCategory(typeCategory) );

        }catch (Exception e){
            return  new APIResponse<>( HttpStatus.NOT_FOUND, true, "Error al actualizar la categoria");
        }

    }

    public APIResponse update(Long id, Category updatedCategory) {
        try {
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
            return new APIResponse("Category Actualizado com sucesso", HttpStatus.OK, false, save(existing));
        }catch (Exception e){
            return  new APIResponse<>( HttpStatus.NOT_FOUND, true, "Error al actualizar la categoria");
        }

    }

    public APIResponse findAll() {
        List<Category> categories = repository.findAll();
        return new APIResponse<>("Categorías obtenidas correctamente", org.springframework.http.HttpStatus.OK, false, categories);
    }

    public APIResponse<Category> findByIdA(Long id) {
        return repository.findById(id)
                .map(cat -> new APIResponse<>("Categoría encontrada", org.springframework.http.HttpStatus.OK, false, cat))
                .orElseGet(() -> new APIResponse<>("Categoría no encontrada", org.springframework.http.HttpStatus.NOT_FOUND, true, null));
    }
    public APIResponse categoryTypeList() {

      return  new APIResponse("", HttpStatus.OK, false, categoryRepository.findAll()
              .stream()
              .map(Category::getTypeCategory)
              .distinct()
              .collect(Collectors.toList()));
    }
}
