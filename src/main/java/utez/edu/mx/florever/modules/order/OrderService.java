package utez.edu.mx.florever.modules.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public List<Order> findAllByStatus(String status) {
        return orderRepository.findAllByStatus(status);
    }
    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }
}
