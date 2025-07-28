package utez.edu.mx.florever.modules.floristas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FloristasRepository extends JpaRepository<Floristas, Long> {
    List<Floristas> findAll();
    Optional<Floristas> findById(Long id);
    Floristas save(Floristas floristas); // Este guarda y actualiza

    @Modifying
    @Query(value = "DELETE FROM floristas WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}