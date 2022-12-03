package hello.jwt;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findOne(String tokenValue);

    void delete(String tokenValue);
}
