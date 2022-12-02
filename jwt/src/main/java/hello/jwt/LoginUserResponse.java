package hello.jwt;

import lombok.Getter;

@Getter
public class LoginUserResponse {
    private Long id;
    private String username;

    public LoginUserResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static LoginUserResponse from(User user) {
        return new LoginUserResponse(user.getId(), user.getUsername());
    }
}
