package hello.jwt;

import lombok.Getter;

@Getter
public class TokenResult {
    private final String accessToken;
    private final String refreshToken;

    public TokenResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResult of(String accessToken, RefreshToken refreshToken) {
        return new TokenResult(accessToken, refreshToken.getRefreshToken());
    }
}
