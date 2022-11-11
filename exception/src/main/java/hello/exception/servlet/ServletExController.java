package hello.exception.servlet;

import jdk.jfr.Frequency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {


    // All defined in RequestDispatcher
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @GetMapping("/error")
    public void error() {
        throw new RuntimeException();
    }

    @GetMapping("/error500")
    public void error500(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendError(500, "500 error");
        printErrorInfo(req);
    }

    @GetMapping("/error404")
    public void error404(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendError(404, "404 error");
        printErrorInfo(req);
    }

    private void printErrorInfo(HttpServletRequest req) {
        log.info("ERROR_EXCEPTION={}", ERROR_EXCEPTION);
        log.info("ERROR_EXCEPTION_TYPE={}", ERROR_EXCEPTION_TYPE);
        log.info("ERROR_MESSAGE={}", ERROR_MESSAGE);
        log.info("ERROR_REQUEST_URI={}", ERROR_REQUEST_URI);
        log.info("ERROR_SERVLET_NAME={}", ERROR_SERVLET_NAME);
        log.info("ERROR_STATUS_CODE={}", ERROR_STATUS_CODE);
        log.info("dispatchType={}", req.getDispatcherType());
    }
}
