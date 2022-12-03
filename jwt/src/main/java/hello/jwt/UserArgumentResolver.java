package hello.jwt;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    public UserArgumentResolver(JwtProvider jwtProvider) {
        System.out.println("UserArgumentResolver.UserArgumentResolver");
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println("UserArgumentResolver.supportsParameter");
        return parameter.hasParameterAnnotation(Verified.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        System.out.println("UserArgumentResolver.resolveArgument");
        String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("header = " + header);
        if (header == null) {
            return null;
        }
        return jwtProvider.getPayload(header);
    }
}
