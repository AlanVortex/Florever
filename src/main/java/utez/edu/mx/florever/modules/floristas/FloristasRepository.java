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

    // Métodos adicionales específicos para floristas
    Optional<Floristas> findByCorreo(String correo);

    List<Floristas> findByEstado(String estado);

    @Query(value = "SELECT * FROM floristas WHERE estado = 'activo'", nativeQuery = true)
    List<Floristas> findActiveFloristas();

    @Query(value = "SELECT * FROM floristas WHERE nombre_completo LIKE %:nombre% OR correo LIKE %:correo%", nativeQuery = true)
    List<Floristas> findByNombreOrCorreo(@Param("nombre") String nombre, @Param("correo") String correo);
}