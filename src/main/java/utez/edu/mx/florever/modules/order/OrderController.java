package utez.edu.mx.florever.modules.order;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.florever.modules.order.dto.DtoOrderPayload;
import utez.edu.mx.florever.utils.APIResponse;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Controlador de Ordenes", description = "Operaciones relacionadas con ordenes")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<APIResponse> save (@RequestBody DtoOrderPayload payload, HttpServletRequest req) {
        APIResponse response = orderService.save(payload, req);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
