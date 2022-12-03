package hello.jwt;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenExtractor {

    public String extractToken(String authHeader, String tokenType) {
        System.out.println("AuthTokenExtractor.extractToken");
        System.out.println("authHeader = " + authHeader);
        System.out.println("tokenType = " + tokenType);
        if (authHeader == null) throw new RuntimeException();

        String[] headers = authHeader.split(" ");
        for (String header : headers) {
            System.out.println("header = " + header);
        }

        if (headers.length != 2) throw new RuntimeException();
        if (!headers[0].equalsIgnoreCase(tokenType)) throw new RuntimeException();

        return headers[1];
    }
}
