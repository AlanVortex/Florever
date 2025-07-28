package utez.edu.mx.florever.modules.floristas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import utez.edu.mx.florever.modules.order.Order;

import java.util.List;

@Entity
@Table(name = "floristas")
public class Floristas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ][\\sA-Za-zÁÉÍÓÚáéíóúÑñ]{2,}$",
            message = "Solo se aceptan letras y espacios, mínimo 3 caracteres"
    )
    @NotNull(message = "Ingresa el nombre completo del florista")
    @NotBlank(message = "El nombre completo no puede estar vacío")
    @Column(name = "nombre_completo", nullable = false)
    private String fullname;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "El teléfono debe tener entre 10 y 15 dígitos"
    )
    @NotNull(message = "Ingresa el teléfono")
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(name = "telefono", nullable = false, length = 20)
    private String telephone;

    @Email(message = "Ingresa un correo electrónico válido")
    @NotNull(message = "Ingresa el correo electrónico")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(name = "correo", nullable = false, unique = true, length = 255)
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número"
    )
    @NotNull(message = "Ingresa la contraseña")
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "password", nullable = false)
    private String password;

    @Pattern(
            regexp = "^(activo|inactivo)$",
            message = "El estado debe ser 'activo' o 'inactivo'"
    )
    @NotNull(message = "Ingresa el estado")
    @Column(name = "estado", nullable = false)
    private String status;

    @OneToMany(mappedBy = "florista")
    private List<Order> orders;

    public Floristas() {
    }

    public Floristas(Long id, String fullname, String telephone, String password, String email, String status, List<Order> orders) {
        this.id = id;
        this.fullname = fullname;
        this.telephone = telephone;
        this.password = password;
        this.email = email;
        this.status = status;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}