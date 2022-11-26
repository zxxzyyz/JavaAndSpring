package Basic.MemberItemApp.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String id = UUID.randomUUID().toString();

        try {
            log.info("try [{}] [{}]", id, requestURI);
            chain.doFilter(request, response);
        } catch (Exception ex) {
            log.info("exception [{}] [{}]", id, requestURI);
            throw ex;
        } finally {
            log.info("finally [{}] [{}]", id, requestURI);
        }
    }
}
