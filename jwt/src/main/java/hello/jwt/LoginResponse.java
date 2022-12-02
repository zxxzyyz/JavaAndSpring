package hello.jwt;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String accessToken;
    private boolean userRegistration;
    private LoginUserResponse user;

    public LoginResponse(String accessToken, boolean userRegistration, LoginUserResponse user) {
        this.accessToken = accessToken;
        this.userRegistration = userRegistration;
        this.user = user;
    }

    public static LoginResponse from(LoginResult loginResult) {
        User user = loginResult.getUser();
        LoginUserResponse loginUserResponse = LoginUserResponse.from(user);
        return new LoginResponse(
                loginResult.getAccessToken(),
                user.isRegistered(),
                loginUserResponse);
    }
}
