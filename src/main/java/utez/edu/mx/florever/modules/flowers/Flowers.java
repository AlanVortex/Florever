package utez.edu.mx.florever.modules.flowers;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

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
    @NotNull(message = "Ingresa el nombre de la flor")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ][\\sA-Za-zÁÉÍÓÚáéíóúÑñ]{2,}$",
            message = "Solo se aceptan letras y espacios, mínimo 3 caracteres"
    )
    @NotNull(message = "Ingresa el tipo de flor")
    @NotBlank(message = "El tipo no puede estar vacío")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @NotNull(message = "Ingresa el precio")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @NotNull(message = "Ingresa la cantidad")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,;:\\s]{10,}$",
            message = "La descripción debe tener al menos 10 caracteres"
    )
    @NotNull(message = "Ingresa la descripción")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Pattern(
            regexp = "^(https?://[\\w\\.-]+\\.[a-zA-Z]{2,}(/[\\w\\.-]*)*\\.(jpg|jpeg|png|gif|webp))$",
            message = "La imagen debe ser una URL válida con formato jpg, jpeg, png, gif o webp"
    )
    @Column(name = "imagen", length = 255)
    private String imagen;

    // Constructor completo
    public Flowers(Long id, String nombre, String tipo, BigDecimal precio, Integer cantidad, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    // Constructor vacío
    public Flowers() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}