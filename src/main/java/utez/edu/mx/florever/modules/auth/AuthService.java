package utez.edu.mx.florever.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.security.jwt.JWTUtils;

import java.sql.SQLException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UDServices udService;

    @Autowired
    private JWTUtils jwtUtils;

    @Transactional(readOnly = true)
    public APIResponse doLogin(LoginRequestDTO payload) {
        try {
            BeanUser found = userRepository.findByUsername(payload.getUsername()).orElse(null);
            if (found == null) return new APIResponse("Usuario no encontrado", true, HttpStatus.NOT_FOUND);

            if (!PasswordEncoder.verifyPassword(payload.getPassword(), found.getPassword()))
                return new APIResponse("Las contrasenas no coniciden", true, HttpStatus.BAD_REQUEST);

            UserDetails ud = udService.loadUserByUsername(found.getUsername());
            String token = jwtUtils.generateToken(ud);
            return new APIResponse("Operacion exitosa", token, false, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(
                    "Error al iniciar sesion",
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public APIResponse register (BeanUser payload){
        try {
            BeanUser found= userRepository.findByUsername(payload.getUsername()).orElse(null);
            if(found != null) return new APIResponse("El usuario ya existe", true, HttpStatus.BAD_REQUEST);

            payload.setPassword(PasswordEncoder.encondePassword(payload.getPassword()));
            userRepository.save(payload);
            return new APIResponse("Operación exitosa", false, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(
                    "Error al registrar al usuario",
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }