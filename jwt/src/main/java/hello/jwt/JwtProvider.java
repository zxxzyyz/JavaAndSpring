package hello.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private static final String TOKE_TYPE = "Bearer";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private final Key secretKey;
    private final long validityInMilliseconds;
    private final AuthTokenExtractor authTokenExtractor;

    public JwtProvider(
            AuthTokenExtractor authTokenExtractor,
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.expire-length}") long validityInMilliseconds) {
        this.authTokenExtractor = authTokenExtractor;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createAccessToken(Long id, String role) {
        System.out.println("JwtProvider.createAccessToken");
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireAt)
                .claim("id", id)
                .claim("role", role)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public UserPayload getPayload(String authHeader) {
        System.out.println("JwtProvider.getPayload");
        String token = authTokenExtractor.extractToken(authHeader, TOKE_TYPE);
        System.out.println("token = " + token);
        Claims body = Jwts.parserBuilder()
                .setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        System.out.println("body = " + body);

        try {
            Long id = body.get("id", Long.class);
            String role = body.get("role", String.class);
            return new UserPayload(id, role);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public boolean isValidToken(String authHeader) {
        String token = authTokenExtractor.extractToken(authHeader, TOKE_TYPE);
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            if (!body.getSubject().equals(ACCESS_TOKEN_SUBJECT)) return false;
            if (body.getExpiration().before(new Date())) return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
