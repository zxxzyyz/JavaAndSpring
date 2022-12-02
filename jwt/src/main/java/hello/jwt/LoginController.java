package hello.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        System.out.println("LoginController.home");
        return "home";
    }

    @GetMapping("/user")
    public String user() {
        System.out.println("LoginController.user");
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        System.out.println("LoginController.admin");
        return "admin";
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login() {





        return ResponseEntity.ok().build();
    }
}
