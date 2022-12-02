package hello.jwt;

import lombok.Getter;

@Getter
public class LoginResult {
    private final String refreshToken;
    private final String accessToken;
    private final User user;

    public LoginResult(String refreshToken, String accessToken, User user) {
        System.out.println("LoginResult.LoginResult");
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.user = user;
    }
}
