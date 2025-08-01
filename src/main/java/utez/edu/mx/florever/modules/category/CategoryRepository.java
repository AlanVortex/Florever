package utez.edu.mx.florever.modules.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByTypeCategory(String typeCategory);
}