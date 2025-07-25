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

    @JsonIgnore
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
}
