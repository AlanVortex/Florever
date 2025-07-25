package utez.edu.mx.florever.modules.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import utez.edu.mx.florever.modules.order.Order;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private int totalQuantityFlowers;
    private String typeCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Order> orders ;



    public Category() {
    }

    public Category(Long id, String name, String description, double price, int totalQuantityFlowers, String typeCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.totalQuantityFlowers = totalQuantityFlowers;
        this.typeCategory = typeCategory;
    }
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalQuantityFlowers() {
        return totalQuantityFlowers;
    }

    public void setTotalQuantityFlowers(int totalQuantityFlowers) {
        this.totalQuantityFlowers = totalQuantityFlowers;
    }

    public String getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(String typeCategory) {
        this.typeCategory = typeCategory;
    }
}