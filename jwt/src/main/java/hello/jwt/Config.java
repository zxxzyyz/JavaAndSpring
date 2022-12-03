package hello.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {
    private static final String LOCAL1 = "http://localhost:3000";
    private static final String LOCAL2 = "http://localhost:5555";
    private static final String LOCAL3 = "http://localhost:8080";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(LOCAL1, LOCAL2, LOCAL3)
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE);
    }
}
