package hello.exception.servlet;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage ep404 = new ErrorPage(HttpStatus.NOT_FOUND, "/ep/404");
        ErrorPage ep500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/ep/500");
        // Subclass of RuntimeException also works the same way
        ErrorPage epEx = new ErrorPage(RuntimeException.class, "/ep/500");
        factory.addErrorPages(ep404, ep500, epEx);
    }
}
