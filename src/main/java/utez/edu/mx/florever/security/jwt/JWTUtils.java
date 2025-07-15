package utez.edu.mx.florever.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//CUARTO PASO : Generar las utilerias para jwt
@Service
public class JWTUtils {
    @Value("${secret.key}")
    private String SECRET_KEY;

    //Esta funcion ayuda a extraer todas la propiedades del token
    //Es decir , el cuerpo del token
    public Claims exctractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    //Esta funcion nos ayuda a poder estraer las propiedades del token
    public <T> T exctractClaim(String token, Function<Claims , T> claimsResolver){
        final  Claims CLAIMS = exctractAllClaims(token);
        return claimsResolver.apply(CLAIMS);
    }

    //Esta funcoin extrae el nombre de usurio del token
    public String exctractUsername(String token){
        return exctractClaim(token , Claims::getSubject);
    }
    //Esta funcion extraer la fecha de expriracion
    public Date exctractExpirationDate(String token){
        return exctractClaim(token , Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return exctractExpirationDate(token).before(new Date());
    }

    //Esta funcion consume a la de arriba
    public Boolean validateToken(String token , UserDetails userDetails){
        final String username = exctractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //Esta funcion nos ayuda a crear nuestro token
    private String createToken(Map<String, Object> claims , String subject){
        return Jwts.builder() //Vamos a contruir un token
                .setClaims(claims).setSubject(subject)//Informacion del usuario
                .setIssuedAt(new Date(System.currentTimeMillis()))//Cuando se creo
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))//Cuanto va a durar
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)//Firamos el token
                .compact();//Compactamos

    }

    //Esta funcion consume la funcion solo para retornar
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims , userDetails.getUsername());
    }

}
