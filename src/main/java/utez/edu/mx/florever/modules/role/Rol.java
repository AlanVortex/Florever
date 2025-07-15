package utez.edu.mx.florever.modules.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import utez.edu.mx.florever.modules.user.BeanUser;

import java.util.List;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , nullable = false)
    private String name;

    @OneToMany(mappedBy = "rol")
    @JsonIgnore
    private List<BeanUser>  users;

    public Rol(Long id, String name, List<BeanUser> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
    public Rol() {}

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

    public List<BeanUser> getUsers() {
        return users;
    }

    public void setUsers(List<BeanUser> users) {
        this.users = users;
    }
}