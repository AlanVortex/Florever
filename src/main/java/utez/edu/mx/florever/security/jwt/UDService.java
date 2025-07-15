package utez.edu.mx.florever.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.modules.user.UserRepository;

import java.util.Collections;

//TERCER PASO : Crear nuestro servicio de gestion de autoridades
@Service
public class UDService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscar primero al usuario
        BeanUser found = userRepository.findByUsername(username).orElse(null);
        if (found == null) throw new UsernameNotFoundException(username);

        //Generar las autoridades para el contexto de seguridad
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + found.getRol().getName());

        //Retornar el objeto de usuario para registrar en el contexto de seguridad

        return new User(
                found.getUsername(),
                found.getPassword(),
                Collections.singleton(authority)
        );
    }
}
