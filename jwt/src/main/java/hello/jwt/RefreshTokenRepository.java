package hello.jwt;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
