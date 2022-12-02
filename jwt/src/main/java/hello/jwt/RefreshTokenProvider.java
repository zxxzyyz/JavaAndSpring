package hello.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenProvider {

    private final long expireLength;

    public RefreshTokenProvider(
            @Value("${security.jwt.expire-length}") long expireLength) {
        this.expireLength = expireLength;
    }

    public RefreshToken createToken(Long id) {
        return null;
    }
}
