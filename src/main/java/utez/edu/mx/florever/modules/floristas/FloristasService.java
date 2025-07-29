package utez.edu.mx.florever.modules.floristas;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.modules.auth.AuthServices;
import utez.edu.mx.florever.modules.role.Rol;
import utez.edu.mx.florever.modules.role.RolRepository;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.modules.user.UserService;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.utils.APIResponse;

import java.sql.SQLException;

@Service
public class FloristasService {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthServices authServices;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    public APIResponse findAll() {
        return userService.findAllFloristas();
    }

    @Transactional(readOnly = true)
    public APIResponse findById(Long id) {
        return userService.findFloristaById(id);
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse save(BeanUser payload, HttpServletRequest request) {
        try {
            // Verificar que el usuario actual es admin
            String token = jwtUtils.resolveToken(request);
            if (token == null) {
                return new APIResponse(HttpStatus.UNAUTHORIZED, true, "Token no proporcionado");
            }

            String currentUserEmail = jwtUtils.exctractUsername(token);
            BeanUser currentUser = userService.getUserByMail(currentUserEmail);

            if (!"ADMIN".equals(currentUser.getRol().getName())) {
                return new APIResponse(HttpStatus.FORBIDDEN, true, "Solo los administradores pueden crear floristas");
            }
            Rol floristaRol = rolRepository.findByName("FLORIST").get();
            payload.setRol(floristaRol);
            // Registrar florista usando AuthServices
            return authServices.registerFlorista(payload);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo registrar el florista");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse update(BeanUser payload, HttpServletRequest request) {
        try {
            // Verificar que el usuario tiene el rol de florista antes de actualizar
            APIResponse floristaResponse = userService.findFloristaById(payload.getId());
            if (floristaResponse.isError()) {
                return floristaResponse;
            }

            // Usar el servicio de usuario para manejar la actualización con las validaciones de permisos
            return userService.updateUser(payload, request);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo actualizar el florista");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse remove(Long id, HttpServletRequest request) {
        try {
            // Verificar que el usuario es un florista antes de eliminar
            APIResponse floristaResponse = userService.findFloristaById(id);
            if (floristaResponse.isError()) {
                return floristaResponse;
            }

            // Usar el servicio de usuario para manejar la eliminación con las validaciones de permisos
            return userService.deleteUser(id, request);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo eliminar el florista");
        }
    }
}