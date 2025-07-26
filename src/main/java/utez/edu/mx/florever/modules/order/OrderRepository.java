package utez.edu.mx.florever.modules.order;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.florever.modules.user.BeanUser;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order,Long> {
    List<Order> findAllByStatus(String status);

    List<Order> findAllByStatusAndFlorist_Id(String status, Long floristId);

    List<Order> findAllByStatusAndFlorist(String status, BeanUser florist);

    List<Order> findByIdAndFlorist(Long id, BeanUser florist);
}
