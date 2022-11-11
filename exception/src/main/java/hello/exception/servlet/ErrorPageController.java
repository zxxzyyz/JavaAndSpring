package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("/ep/404")
    public String ep404(HttpServletRequest req, HttpServletResponse response) {
        log.info("ErrorPageController.ep404");
        return "ep/404";
    }

    @RequestMapping("/ep/500")
    public String ep500(HttpServletRequest req, HttpServletResponse response) {
        log.info("ErrorPageController.ep500");
        return "ep/500";
    }
}
