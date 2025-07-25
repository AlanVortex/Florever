package utez.edu.mx.florever.modules.order;

import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import utez.edu.mx.florever.modules.category.Category;
import utez.edu.mx.florever.modules.orderhasflowers.OrderHasFlowers;
import utez.edu.mx.florever.modules.user.BeanUser;

import java.util.List;

@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "fk_category" , nullable = false)
    private Category category;

    @OneToMany(mappedBy = "order")
    private List<OrderHasFlowers> orderHasFlowers;

   @ManyToOne
   @JoinColumn(name = "fk_user" , nullable = false)
   private BeanUser user;
}
