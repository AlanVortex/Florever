package utez.edu.mx.florever.modules.order;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.florever.modules.order.dto.DtoOrderPayload;
import utez.edu.mx.florever.utils.APIResponse;

import java.util.List;

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
    @GetMapping("/status/{status}")
    public ResponseEntity<APIResponse> allStatus (@PathVariable String status, HttpServletRequest req) {
        APIResponse response = orderService.findAllByStatus(status , req);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PutMapping("/assign/{id}")
    public ResponseEntity<APIResponse> update( @PathVariable Long id,HttpServletRequest req) {
        APIResponse response = orderService.assign(id, req);
        return new ResponseEntity<>(response, response.getStatus());

    }
    @PutMapping("/closed/{id}")
    public ResponseEntity<APIResponse> closed( @PathVariable Long id,HttpServletRequest req) {
        APIResponse response = orderService.closed(id, req);
        return new ResponseEntity<>(response, response.getStatus());

    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> findById(@PathVariable Long id) {
        APIResponse response = orderService.get(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity getOrderHistory(@PathVariable Long userId) {
        APIResponse response = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
