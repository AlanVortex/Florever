package utez.edu.mx.florever.modules.orderhasflowers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import utez.edu.mx.florever.modules.flowers.Flowers;
import utez.edu.mx.florever.modules.order.Order;

import java.util.List;

@Entity
@Table(name = "orden_has_flowers")
public class OrderHasFlowers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int cuantity;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "fk_flowers" , nullable = false)
    private Flowers flowers ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_order" , nullable = false)
    private Order order ;



    public int getCuantity() {
        return cuantity;
    }

    public void setCuantity(int cuantity) {
        this.cuantity = cuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Flowers getFlowers() {
        return flowers;
    }

    public void setFlowers(Flowers flowers) {
        this.flowers = flowers;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
