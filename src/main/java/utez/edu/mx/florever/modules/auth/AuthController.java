package utez.edu.mx.florever.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.florever.modules.auth.dto.LoginRequestDTO;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.utils.APIResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthServices authServices;

    @PostMapping("")
    public ResponseEntity<APIResponse> doLogin(@RequestBody LoginRequestDTO payload) {
        APIResponse response = authServices.doLogin(payload);
        return new ResponseEntity <>(response, response.getStatus());
    }
    @PostMapping("/register")
    public ResponseEntity<APIResponse> register(@RequestBody BeanUser payload) {
        APIResponse response = authServices.register(payload);
        return new ResponseEntity <>(response, response.getStatus());
    }

}