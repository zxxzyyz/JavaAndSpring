package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {

        // Create sessionId and store it
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest req) {
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        return sessionCookie == null ? null : sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest req) {
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        if (sessionCookie != null) sessionStore.remove(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest req, String cookieName) {
        if (req.getCookies() == null) return null;
        return Arrays.stream(req.getCookies()).filter(c -> c.getName().equals(SESSION_COOKIE_NAME)).findAny().orElse(null);
    }
}
