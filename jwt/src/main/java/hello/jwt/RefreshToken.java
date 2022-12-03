package hello.jwt;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class RefreshToken {
    private final String refreshToken;
    private final Long userId;
    private final LocalDateTime expireTime;

    public RefreshToken(String refreshToken, Long userId, LocalDateTime expireTime) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RefreshToken that = (RefreshToken) o;
        return
                Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken, userId, expireTime);
    }
}
