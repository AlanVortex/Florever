package utez.edu.mx.florever.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.modules.auth.dto.LoginRequestDTO;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.modules.user.UserRepository;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.security.jwt.UDServices;
import utez.edu.mx.florever.security.jwt.UDServices;
import utez.edu.mx.florever.utils.APIResponse;
import utez.edu.mx.florever.utils.PasswordEncoder;

import java.sql.SQLException;

@Service
public class AuthServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UDServices udService;

    @Autowired
    private JWTUtils jwtUtils;


    @Transactional(readOnly = true)
    public APIResponse doLogin(LoginRequestDTO payload) {
        BeanUser found = userRepository.findByUsername(payload.getUsername()).orElse(null);
        try {
            if (found == null) {
                return new APIResponse(
                        HttpStatus.NOT_FOUND,
                        true,
                        "Usuario no encontrado"
                );
            }
            if (!PasswordEncoder.verifyPassword(payload.getPassword(), found.getPassword())) {
                return new APIResponse(
                        HttpStatus.BAD_REQUEST,
                        true,
                        "Usuario o contraseña no conciden"
                );
            }
            UserDetails ud = udService.loadUserByUsername(found.getUsername());
            String token = jwtUtils.generateToken(ud);
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, token);
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Error al inicar session");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse register(BeanUser payload) {
        try {
            BeanUser found = userRepository.findByUsername(payload.getUsername()).orElse(null);
            if (found != null) {
                return new APIResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        true,
                        "Usuario ya existe");
            }
            payload.setPassword(PasswordEncoder.encode(payload.getPassword()));
            userRepository.save(payload);
            return new APIResponse("Operacion exitosa", HttpStatus.CREATED, false, "");
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Error al registar usuario");
        }
    }


}