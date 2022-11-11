package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("LogFilter.doFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        String id = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", id, requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("REQUEST [{}][{}]", id, requestURI);
        }

    }

    @Override
    public void destroy() {
        log.info("LogFilter.destroy");
    }
}
