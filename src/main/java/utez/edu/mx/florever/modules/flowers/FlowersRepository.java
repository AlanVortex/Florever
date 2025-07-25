package utez.edu.mx.florever.modules.flowers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlowersRepository extends JpaRepository<Flowers, Long>{
    List<Flowers> findAll();
    Optional<Flowers> findById(Long id);
    Flowers save(Flowers flowers); //Este guarda y actualiza

    @Modifying
    @Query(value = "DELETE FROM flowers WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
