package utez.edu.mx.florever.modules.flowers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.utils.APIResponse;

import java.sql.SQLException;
import java.util.List;

@Service
public class FlowersService {
    @Autowired
    private FlowersRepository flowersRepository;

    @Transactional(readOnly = true)
    public APIResponse findAll() {
        List<Flowers> list = flowersRepository.findAll();
        return new APIResponse("Operacion exitosa", list, false, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public APIResponse findById(Long id) {
        try{
            Flowers found = flowersRepository.findById(id).orElse(null);
            if (found == null) {
                return new APIResponse("Flor no encontrada", true, HttpStatus.NOT_FOUND);
            }
            return new APIResponse("Operacion exitosa", found, false, HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("No se pudo consultar la Flor", true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse save(Flowers payload) {
        try{
            Flowers saved = flowersRepository.save(payload);
            return new APIResponse("Operacion exitosa", saved, false, HttpStatus.CREATED);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("No se pudo registrar la Flor", true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse update(Flowers payload) {
        try{
            if (flowersRepository.findById(payload.getId()).isEmpty()) {
                return new APIResponse("Flor no encontrada", true, HttpStatus.NOT_FOUND);
            }
            Flowers updated = flowersRepository.save(payload);
            return new APIResponse("Operacion exitosa", updated, false, HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("No se pudo actualizar la Flor", true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse remove(Flowers payload) {
        try{
            if (flowersRepository.findById(payload.getId()).isEmpty()) {
                return new APIResponse("Flor no encontrada", true, HttpStatus.NOT_FOUND);
            }
            flowersRepository.deleteById(payload.getId());
            return new APIResponse("Operacion exitosa", false, HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("No se pudo borrar la Flor", true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}