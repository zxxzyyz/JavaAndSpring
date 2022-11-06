package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Content-Type: application/json
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8"); // this is default if content-type is application/json
        HelloData helloData = new HelloData();
        helloData.setUsername("koo");
        helloData.setAge(31);

        // To Json
        String result = objectMapper.writeValueAsString(helloData);
        res.getWriter().write(result);
    }
}
