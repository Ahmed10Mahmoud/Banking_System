package example.wep.app.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // MUST be at least 32 bytes for HS256
    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ========================
    // GENERATE TOKEN
    // ========================
    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles",
                        user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey())
                .compact();
    }

    // ========================
    // EXTRACT USERNAME
    // ========================
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ========================
    // GENERIC CLAIM EXTRACTOR
    // ========================
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // ========================
    // PARSE TOKEN (IMPORTANT FIX)
    // ========================
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ========================
    // VALIDATION
    // ========================
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}