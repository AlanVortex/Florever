package utez.edu.mx.florever.modules.category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int totalQuantityFlowers;
    private String typeCategory;


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