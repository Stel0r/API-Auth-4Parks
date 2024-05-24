package APIFourparks.Backend.Login.Services;

import java.security.AlgorithmConstraints;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {
    private String secretKey = "EstoEsUnaKeySuperSecretaPeroReContraSuperSecretisimaLoMasSecretoQuePudeEncontrar";
    private SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    
    public String generateToken(String user){
        return Jwts.builder().signWith(key).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis()+3600000)).subject(user).compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, String user){
        return user.equals(getClaims(token).getSubject());
    }

    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }
}


