package utez.edu.mx.florever.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static String encode(String rawpassword) {
        return new BCryptPasswordEncoder().encode(rawpassword);
    }
    public static boolean verifyPassword(String rawpassword, String encodedpassword) {
        return new BCryptPasswordEncoder().matches(rawpassword, encodedpassword);
    }


}