package hello.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RefreshTokenCookieProvider {
    private static final String REFRESH_TOKEN ="refreshToken";
    private final Long expiredMills;

    public RefreshTokenCookieProvider(
            @Value("${security.refresh.expire-length}") Long expiredMills) {
        this.expiredMills = expiredMills;
    }

    public ResponseCookie createCookie(String refreshToken) {
        System.out.println("RefreshTokenCookieProvider.createCookie");
        return createCookieBuilder(refreshToken)
                .maxAge(Duration.ofMillis(expiredMills))
                .build();
    }

    private ResponseCookieBuilder createCookieBuilder(String refreshToken) {
        System.out.println("RefreshTokenCookieProvider.createCookieBuilder");
        System.out.println("refreshToken = " + refreshToken);
        ResponseCookieBuilder builder = ResponseCookie.from(REFRESH_TOKEN, refreshToken);
        System.out.println("builder = " + builder.toString());
        builder.httpOnly(true);
        System.out.println("builder.httpOnly() = " + builder.toString());
        builder.secure(true);
        System.out.println("builder.secure() = " + builder.toString());
        builder.path("/");
        System.out.println("builder.path() = " + builder.toString());
        builder.sameSite(Cookie.SameSite.NONE.attributeValue());
        System.out.println("builder.sameSite() = " + builder.toString());
        return builder;
    }
}
