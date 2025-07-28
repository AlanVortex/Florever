package utez.edu.mx.florever.modules.floristas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.utils.APIResponse;

import java.sql.SQLException;
import java.util.List;

@Service
public class FloristasService {
    @Autowired
    private FloristasRepository floristasRepository;

    @Transactional(readOnly = true)
    public APIResponse findAll() {
        List<Floristas> list = floristasRepository.findAll();
        return new APIResponse("Operacion exitosa", HttpStatus.OK, false, list);
    }

    @Transactional(readOnly = true)
    public APIResponse findById(Long id) {
        try {
            Floristas found = floristasRepository.findById(id).orElse(null);
            if (found == null) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Florista no encontrado");
            }
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, found);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo consultar el Florista");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse save(Floristas payload) {
        try {
            Floristas saved = floristasRepository.save(payload);
            return new APIResponse("Operacion exitosa", HttpStatus.CREATED, false, saved);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo registrar el Florista");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse update(Floristas payload) {
        try {
            if (floristasRepository.findById(payload.getId()).isEmpty()) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Florista no encontrado");
            }
            Floristas updated = floristasRepository.save(payload);
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, updated);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo actualizar el Florista");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse remove(Floristas payload) {
        try {
            if (floristasRepository.findById(payload.getId()).isEmpty()) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Florista no encontrado");
            }
            floristasRepository.deleteById(payload.getId());
            return new APIResponse(HttpStatus.OK, false, "Operacion exitosa");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo borrar el Florista");
        }
    }

    // Métodos adicionales específicos para floristas
    @Transactional(readOnly = true)
    public APIResponse findByCorreo(String correo) {
        try {
            Floristas found = floristasRepository.findByCorreo(correo).orElse(null);
            if (found == null) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Florista no encontrado con ese correo");
            }
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, found);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo consultar el Florista por correo");
        }
    }

    @Transactional(readOnly = true)
    public APIResponse findByEstado(String estado) {
        try {
            List<Floristas> list = floristasRepository.findByEstado(estado);
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, list);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo consultar floristas por estado");
        }
    }

    @Transactional(readOnly = true)
    public APIResponse findActiveFloristas() {
        try {
            List<Floristas> list = floristasRepository.findActiveFloristas();
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false, list);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo consultar floristas activos");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse cambiarEstado(Long id, String nuevoEstado) {
        try {
            Floristas florista = floristasRepository.findById(id).orElse(null);
            if (florista == null) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Florista no encontrado");
            }
            florista.setEstado(nuevoEstado);
            Floristas updated = floristasRepository.save(florista);
            return new APIResponse("Estado cambiado exitosamente", HttpStatus.OK, false, updated);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo cambiar el estado del florista");
        }
    }
}