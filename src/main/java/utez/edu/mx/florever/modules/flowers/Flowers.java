package utez.edu.mx.florever.modules.flowers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import utez.edu.mx.florever.modules.orderhasflowers.OrderHasFlowers;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "flowers")
public class Flowers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ][\\sA-Za-zÁÉÍÓÚáéíóúÑñ]{2,}$",
            message = "Solo se aceptan letras y espacios, mínimo 3 caracteres"
    )
    @NotNull(message = "Ingresa el name de la flor")
    @NotBlank(message = "El name no puede estar vacío")
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ][\\sA-Za-zÁÉÍÓÚáéíóúÑñ]{2,}$",
            message = "Solo se aceptan letras y espacios, mínimo 3 caracteres"
    )
    @NotNull(message = "Ingresa el type de flor")
    @NotBlank(message = "El type no puede estar vacío")
    @Column(name = "type", nullable = false)
    private String type;

    @DecimalMin(value = "0.01", message = "El price debe ser mayor a 0")
    @NotNull(message = "Ingresa el price")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Min(value = 0, message = "La amount no puede ser negativa")
    @NotNull(message = "Ingresa la amount")
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,;:\\s]{10,}$",
            message = "La descripción debe tener al menos 10 caracteres"
    )
    @NotNull(message = "Ingresa la descripción")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Pattern(
            regexp = "^(https?://[\\w\\.-]+\\.[a-zA-Z]{2,}(/[\\w\\.-]*)*\\.(jpg|jpeg|png|gif|webp))$",
            message = "La image debe ser una URL válida con formato jpg, jpeg, png, gif o webp"
    )
    @Column(name = "image", length = 255)
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "flowers")
    private List<OrderHasFlowers> orderHasFlowers;

    // Constructor completo
    public Flowers(Long id, String name, String type, BigDecimal price, Integer amount, String description, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.image = image;
    }

    // Constructor vacío
    public Flowers() {
    }

    // Getters and Setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OrderHasFlowers> getOrderHasFlowers() {
        return orderHasFlowers;
    }

    public void setOrderHasFlowers(List<OrderHasFlowers> orderHasFlowers) {
        this.orderHasFlowers = orderHasFlowers;
    }
}