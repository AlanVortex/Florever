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

    public Order(Long id, Double totalPrice, String status, Category category, List<OrderHasFlowers> orderHasFlowers, BeanUser user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.category = category;
        this.orderHasFlowers = orderHasFlowers;
        this.user = user;
    }
    public Order() {}

    public BeanUser getUser() {
        return user;
    }

    public void setUser(BeanUser user) {
        this.user = user;
    }

    public List<OrderHasFlowers> getOrderHasFlowers() {
        return orderHasFlowers;
    }

    public void setOrderHasFlowers(List<OrderHasFlowers> orderHasFlowers) {
        this.orderHasFlowers = orderHasFlowers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
