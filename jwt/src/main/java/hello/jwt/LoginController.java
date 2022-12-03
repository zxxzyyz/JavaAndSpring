package hello.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @GetMapping("/")
    public String home() {
        System.out.println("LoginController.home");
        return "home";
    }

    @Login
    @GetMapping("/user")
    public String user() {
        System.out.println("LoginController.user");
        return "user";
    }

    @Login(admin = true)
    @GetMapping("/admin")
    public String admin() {
        System.out.println("LoginController.admin");
        return "admin";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login() {
        User user = new User();
        user.setUsername("name");
        user.setPassword("pwd");
        user.setRole("USER");
        user.setRegistered(true);

        LoginResult loginResult = loginService.login(user);
        String refreshToken = loginResult.getRefreshToken();
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie(refreshToken);
        System.out.println("cookieCreated");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(loginResult));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @CookieValue(value = RefreshTokenCookieProvider.REFRESH_TOKEN, required = false)
            String refreshToken) {

        if (refreshToken == null) throw new RuntimeException();
        TokenResult tokenResult = loginService.refresh(refreshToken);
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie(
                tokenResult.getRefreshToken()
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenResponse(tokenResult.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(value = RefreshTokenCookieProvider.REFRESH_TOKEN, required = false)
            String refreshToken) {
        if (refreshToken == null) throw new RuntimeException();
        return ResponseEntity.noContent()
                .header(
                    HttpHeaders.SET_COOKIE,
                    refreshTokenCookieProvider.createExpiredCookie().toString())
                .build();
    }
}