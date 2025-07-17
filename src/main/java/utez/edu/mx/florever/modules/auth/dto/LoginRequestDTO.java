package utez.edu.mx.florever.modules.auth.dto;


public class LoginRequestDTO {

    private String email;
    private String password;

    // Constructor vacío
    public LoginRequestDTO() {
    }

    // Constructor con todos los campos
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y Setters


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
}
