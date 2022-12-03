package hello.jwt;

import lombok.Getter;

@Getter
public class UserPayload {
    private final Long id;
    private final String role;

    public UserPayload(Long id, String role) {
        this.id = id;
        this.role = role;
    }


}
