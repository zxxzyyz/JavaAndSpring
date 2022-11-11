package hello.exception.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ServletExController {

    @GetMapping("/error")
    public void error() {
        throw new RuntimeException();
    }

    @GetMapping("/error500")
    public void error500(HttpServletResponse res) throws IOException {
        res.sendError(500, "500 error");
    }

    @GetMapping("/error404")
    public void error404(HttpServletResponse res) throws IOException {
        res.sendError(404, "404 error");
    }
}
