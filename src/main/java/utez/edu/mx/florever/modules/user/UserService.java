package utez.edu.mx.florever.modules.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.modules.role.Rol;
import utez.edu.mx.florever.modules.role.RolRepository;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.utils.APIResponse;
import utez.edu.mx.florever.utils.PasswordEncoder;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private JWTUtils jwtUtils;

    public BeanUser getUserByMail(String mail){
        return userRepository.findByEmail(mail).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public APIResponse findAllFloristas() {
        try {
            Rol floristaRole = rolRepository.findByName("FLORIST").orElse(null);
            if (floristaRole == null) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Rol florista no encontrado");
            }

            List<BeanUser> floristas = userRepository.findByRol(floristaRole);
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, floristas);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudieron consultar los floristas");
        }
    }

    @Transactional(readOnly = true)
    public APIResponse findFloristaById(Long id) {
        try {
            Optional<BeanUser> found = userRepository.findById(id);
            if (found.isEmpty()) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Florista no encontrado");
            }

            BeanUser user = found.get();
            if (!"FLORIST".equals(user.getRol().getName())) {
                return new APIResponse(HttpStatus.BAD_REQUEST, true, "El usuario no es un florista");
            }

            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo consultar el florista");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse updateUser(BeanUser payload, HttpServletRequest request) {
        try {
            // Obtener usuario actual del token
            String token = jwtUtils.resolveToken(request);
            if (token == null) {
                return new APIResponse(HttpStatus.UNAUTHORIZED, true, "Token no proporcionado");
            }
            String currentUserEmail = jwtUtils.exctractUsername(token);
            BeanUser currentUser = getUserByMail(currentUserEmail);

            // Verificar que el usuario existe
            Optional<BeanUser> existingUser = userRepository.findById(payload.getId());
            if (existingUser.isEmpty()) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Usuario no encontrado");
            }

            BeanUser userToUpdate = existingUser.get();
            String userRole = userToUpdate.getRol().getName();

            // Validaciones de permisos
            if ("FLORIST".equals(userRole)) {
                // Si es florista, solo puede editarse a sí mismo
                if (!currentUser.getId().equals(userToUpdate.getId())) {
                    // A menos que el usuario actual sea admin
                    if (!"ADMIN".equals(currentUser.getRol().getName())) {
                        return new APIResponse(HttpStatus.FORBIDDEN, true, "No tienes permisos para editar este florista");
                    }
                }

                // Un florista no puede cambiar su propio rol
                if (currentUser.getId().equals(userToUpdate.getId()) &&
                        payload.getRol() != null &&
                        !payload.getRol().getId().equals(userToUpdate.getRol().getId())) {
                    return new APIResponse(HttpStatus.FORBIDDEN, true, "No puedes cambiar tu propio rol");
                }
            } else if ("ADMIN".equals(userRole)) {
                // Solo admin puede editar otros admins
                if (!"ADMIN".equals(currentUser.getRol().getName())) {
                    return new APIResponse(HttpStatus.FORBIDDEN, true, "No tienes permisos para editar administradores");
                }
            }

            // Actualizar campos permitidos
            if (payload.getName() != null) {
                userToUpdate.setName(payload.getName());
            }
            if (payload.getPhone() != null) {
                userToUpdate.setPhone(payload.getPhone());
            }
            if (payload.getEmail() != null) {
                userToUpdate.setEmail(payload.getEmail());
            }
            if (payload.getPassword() != null && !payload.getPassword().trim().isEmpty()) {
                userToUpdate.setPassword(PasswordEncoder.encode(payload.getPassword()));
            }
            if (payload.getStatus() != null) {
                userToUpdate.setStatus(payload.getStatus());
            }

            // Solo admin puede cambiar roles
            if (payload.getRol() != null && "ADMIN".equals(currentUser.getRol().getName())) {
                userToUpdate.setRol(payload.getRol());
            }

            BeanUser updated = userRepository.save(userToUpdate);
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, updated);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo actualizar el usuario");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse deleteUser(Long userId, HttpServletRequest request) {
        try {
            // Obtener usuario actual del token
            String token = jwtUtils.resolveToken(request);
            if (token == null) {
                return new APIResponse(HttpStatus.UNAUTHORIZED, true, "Token no proporcionado");
            }
            String currentUserEmail = jwtUtils.exctractUsername(token);
            BeanUser currentUser = getUserByMail(currentUserEmail);

            // Verificar que el usuario a eliminar existe
            Optional<BeanUser> userToDelete = userRepository.findById(userId);
            if (userToDelete.isEmpty()) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Usuario no encontrado");
            }

            BeanUser targetUser = userToDelete.get();
            String targetUserRole = targetUser.getRol().getName();

            // Solo admin puede eliminar floristas y otros usuarios
            if (!"ADMIN".equals(currentUser.getRol().getName())) {
                return new APIResponse(HttpStatus.FORBIDDEN, true, "No tienes permisos para eliminar usuarios");
            }

            // No permitir que el admin se elimine a sí mismo
            if (currentUser.getId().equals(targetUser.getId())) {
                return new APIResponse(HttpStatus.BAD_REQUEST, true, "No puedes eliminarte a ti mismo");
            }

            userRepository.deleteById(userId);
            return new APIResponse("Usuario eliminado exitosamente", HttpStatus.OK, false, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo eliminar el usuario");
        }
    }
}