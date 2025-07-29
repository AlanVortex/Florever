package utez.edu.mx.florever.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.florever.modules.role.Rol;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BeanUser,Long> {
    Optional<BeanUser> findByEmailAndPassword(String email, String password);
    Optional<BeanUser> findByEmail(String email);
    List<BeanUser> findByRol(Rol rol);
}