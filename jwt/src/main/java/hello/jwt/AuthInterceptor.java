package hello.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.*;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    public AuthInterceptor(JwtProvider jwtProvider) {
        System.out.println("AuthInterceptor.AuthInterceptor");
        System.out.println("AuthInterceptor.AuthInterceptor");
        System.out.println("AuthInterceptor.AuthInterceptor");
        System.out.println("AuthInterceptor.AuthInterceptor");
        System.out.println("AuthInterceptor.AuthInterceptor");
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AuthInterceptor.preHandle");
        HandlerMethod hm = (HandlerMethod) handler;
        if (!(handler instanceof HandlerMethod)) return true;
        Login auth = hm.getMethodAnnotation(Login.class);
        System.out.println("auth(Login annotation) = " + auth);
        if (auth == null) return true;
        String authHeader = request.getHeader(AUTHORIZATION);
        System.out.println("authHeader = " + authHeader);

        if (request.getHeader(AUTHORIZATION) != null) {
            if (!jwtProvider.isValidToken(authHeader)) {
                System.out.println("Invalid Token");
                // TokenExpiredException
                throw new RuntimeException();
            }

            if (auth.admin()) {
                System.out.println("Admin required");
                UserPayload payload = jwtProvider.getPayload(authHeader);
                if (payload.getRole() != "admin") {
                    System.out.println("No admin, but admin required");
                    // NotAdminException
                    throw new RuntimeException();
                }
            }
            System.out.println("Has auth, return true");
            return true;
        }

        if (auth.required()) {
            System.out.println("No auth, but auth required");
            // TokenNotExistsException
            throw new RuntimeException();
        }
        System.out.println("No auth, return true");
        return true;
    }
}
