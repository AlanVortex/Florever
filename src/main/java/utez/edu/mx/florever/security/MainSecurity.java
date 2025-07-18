package utez.edu.mx.florever.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import utez.edu.mx.florever.security.filters.JWTFilter;

import java.util.List;
//SEXTO PASO : REGISTRAR EL FILTRO DE MAIN SECURITY ,
@Configuration
@EnableWebSecurity
public class MainSecurity {
    private final String[] SWAGGER_PATHS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-resources/**",
            "/webjars/**"
    };

    private final String[] WHITE_LIST = {
            "/api/auth/**",
            "/api/categories/**",
    };

    @Autowired
    private JWTFilter jwtFilter;
    //Roles : ADMIN , EMPLOYEE , CUSTOMER , ETC.
    //ROLES EN ARCHIVOS : ROLES_ADMIM , ROLE_EMPLOYEE , ROLE_CUSTOMER ..(ROLE)


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsRegister()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/client/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/cede/**").hasAnyRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(SWAGGER_PATHS).permitAll()
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
               // .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private CorsConfigurationSource corsRegister() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public UserDetailsService generateUser(){
        //Configurar todos los usuarios de basic
        UserDetails admin = User.builder()
                .username("root")
                .password(passwordEncoder().encode("root"))
                .roles("ADMIN")
                .build();
        UserDetails employee = User.builder()
                .username("albert")
                .password(passwordEncoder().encode("corazonmagico"))
                .roles("EMPLOYEE")
                .build();
        UserDetails swaggerAdmin = User.builder()
                .username("swaggeradmin")
                .password(passwordEncoder().encode("#root1234"))
                .roles("DEV")
                .build();

        return new InMemoryUserDetailsManager(admin,employee,swaggerAdmin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
