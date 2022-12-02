package hello.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class RefreshTokenProvider {

    private final long expireLength;

    public RefreshTokenProvider(
            @Value("${security.jwt.expire-length}") long expireLength) {
        this.expireLength = expireLength;
    }

    public RefreshToken createToken(Long id) {
        System.out.println("RefreshTokenProvider.createToken");
        int days = (int) TimeUnit.MILLISECONDS.toDays(expireLength);
        LocalDateTime time = LocalDateTime.now().plusDays(days);
        return new RefreshToken(UuidUtil.getTimeBasedUuid().toString(), id, time);
    }
}
