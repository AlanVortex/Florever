package utez.edu.mx.florever.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<BeanUser,Long> {
    Optional<BeanUser> findByEmailAndPassword(String email ,  String password);
    Optional<BeanUser> findByEmail(String email);
}