package hello.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class Config implements WebMvcConfigurer {
    private static final String LOCAL1 = "http://localhost:3000";
    private static final String LOCAL2 = "http://localhost:5555";
    private static final String LOCAL3 = "http://localhost:8080";

    private final List<HandlerInterceptor> interceptors;
    private final List<HandlerMethodArgumentResolver> resolvers;

    public Config(List<HandlerInterceptor> interceptors, List<HandlerMethodArgumentResolver> resolvers) {
        this.interceptors = interceptors;
        this.resolvers = resolvers;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        System.out.println("Config.addArgumentResolvers");
        resolvers.addAll(this.resolvers);
        for (HandlerMethodArgumentResolver resolver : this.resolvers) {
            System.out.println("resolver = " + resolver);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("Config.addInterceptors");
        interceptors.forEach(registry::addInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(LOCAL1, LOCAL2, LOCAL3)
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE);
    }
}
