package Basic.MemberItemApp.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String LOGGING_ID = "loggingID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String id = UUID.randomUUID().toString().substring(0, 8);

        request.setAttribute(LOGGING_ID, id);

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("[{}] preHandle. URI=[{}] Handler=[{}]", id, requestURI, handler);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String id = (String) request.getAttribute(LOGGING_ID);
        log.info("[{}] postHandle. MAV=[{}]", id, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String id = (String) request.getAttribute(LOGGING_ID);
        String requestURI = request.getRequestURI();
        log.info("[{}] afterCompletion. URI=[{}] Handler=[{}] Exception=[{}]", id, requestURI, handler, ex);
    }
}
