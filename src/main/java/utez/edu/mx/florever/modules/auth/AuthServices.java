package utez.edu.mx.florever.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.modules.auth.dto.LoginRequestDTO;
import utez.edu.mx.florever.modules.role.Rol;
import utez.edu.mx.florever.modules.role.RolRepository;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.modules.user.UserRepository;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.security.jwt.UDServices;
import utez.edu.mx.florever.utils.APIResponse;
import utez.edu.mx.florever.utils.PasswordEncoder;

import java.sql.SQLException;

@Service
public class AuthServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UDServices udService;

    @Autowired
    private JWTUtils jwtUtils;

    @Transactional(readOnly = true)
    public APIResponse doLogin(LoginRequestDTO payload) {
        BeanUser found = userRepository.findByEmail(payload.getEmail()).orElse(null);
        try {
            if (found == null) {
                return new APIResponse(
                        HttpStatus.NOT_FOUND,
                        true,
                        "Correo no encontrado"
                );
            }
            if (!PasswordEncoder.verifyPassword(payload.getPassword(), found.getPassword())) {
                return new APIResponse(
                        HttpStatus.BAD_REQUEST,
                        true,
                        "Correo o contraseña no conciden"
                );
            }
            UserDetails ud = udService.loadUserByUsername(found.getEmail());
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
            BeanUser found = userRepository.findByEmail(payload.getEmail()).orElse(null);
            if (found != null) {
                return new APIResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        true,
                        "Usuario ya existe");
            }

            // Encripta la contraseña
            payload.setPassword(PasswordEncoder.encode(payload.getPassword()));

            // Si no tiene rol asignado, asignar rol "cliente" por defecto
            if (payload.getRol() == null) {
                Rol clienteRole = rolRepository.findByName("cliente").orElse(null);
                if (clienteRole != null) {
                    payload.setRol(clienteRole);
                }
            }

            userRepository.save(payload);

            return new APIResponse("Operación exitosa", HttpStatus.CREATED, false, "");
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Error al registrar usuario");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse registerFlorista(BeanUser payload) {
        try {
            BeanUser found = userRepository.findByEmail(payload.getEmail()).orElse(null);
            if (found != null) {
                return new APIResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        true,
                        "Usuario ya existe");
            }

            // Encripta la contraseña
            payload.setPassword(PasswordEncoder.encode(payload.getPassword()));

            // Asignar rol de florista automáticamente
            Rol floristaRole = rolRepository.findByName("florista").orElse(null);
            if (floristaRole == null) {
                return new APIResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        true,
                        "Rol de florista no encontrado en el sistema");
            }

            payload.setRol(floristaRole);
            userRepository.save(payload);

            return new APIResponse("Florista registrado exitosamente", HttpStatus.CREATED, false, "");
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Error al registrar florista");
        }
    }
}