package utez.edu.mx.florever.modules.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public BeanUser getUserByMail(String mail){
        return userRepository.findByEmail(mail).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
