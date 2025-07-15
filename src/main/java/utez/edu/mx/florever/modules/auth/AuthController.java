package utez.edu.mx.florever.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<APIResponse> doLogin(@RequestBody LoginRequestDTO payload){
        APIResponse response = authService.doLogin(payload);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> doRegister(@RequestBody BeanUser payload){
        APIResponse response = authService.register(payload);
        return new ResponseEntity<>(response, response.getStatus());
    }
}