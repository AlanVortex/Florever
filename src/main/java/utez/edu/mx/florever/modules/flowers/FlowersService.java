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
        return new APIResponse("Operacion exitosa", HttpStatus.OK,false, list);
    }

    @Transactional(readOnly = true)
    public APIResponse findById(Long id) {
        try{
            Flowers found = flowersRepository.findById(id).orElse(null);
            if (found == null) {
                return new APIResponse(  HttpStatus.NOT_FOUND,true,"Flor no encontrada");
            }
            return new APIResponse("Operacion exitosa"  , HttpStatus.OK,false,found);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo consultar la Flor");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse save(Flowers payload) {
        try{
            Flowers saved = flowersRepository.save(payload);
            return new APIResponse("Operacion exitosa", HttpStatus.CREATED, false,saved);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo registrar la Flor");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse update(Flowers payload) {
        try{
            if (flowersRepository.findById(payload.getId()).isEmpty()) {
                return new APIResponse(HttpStatus.NOT_FOUND, true, "Flor no encontrada");
            }
            Flowers updated = flowersRepository.save(payload);
            return new APIResponse("Operacion exitosa", HttpStatus.OK, false,updated);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo actualizar la Flor");
        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse remove(Flowers payload) {
        try{
            if (flowersRepository.findById(payload.getId()).isEmpty()) {
                return new APIResponse(  HttpStatus.NOT_FOUND,true,"Flor no encontrada");
            }
            flowersRepository.deleteById(payload.getId());
            return new APIResponse(HttpStatus.OK, false, "Operacion exitosa");
        }catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "No se pudo borrar la Flor");
        }
    }
}