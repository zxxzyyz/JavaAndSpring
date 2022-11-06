package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // status line
        res.setStatus(HttpServletResponse.SC_OK);

        // response headers
        res.setHeader("Content-Type", "text/plain;charset=utf-8");
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("my-header", "hello");

        // header method
        content(res);
        cookie(res);
        redirect(res);

        // response body
        PrintWriter writer = res.getWriter();
        writer.println("ok");
    }

    private void content(HttpServletResponse res) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //res.setHeader("Content-Type", "text/plain;charset=utf-8");
        res.setContentType("text/plain");
        res.setCharacterEncoding("utf-8");
        //res.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse res) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //res.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        res.addCookie(cookie);
    }

    private void redirect(HttpServletResponse res) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //res.setStatus(HttpServletResponse.SC_FOUND); //302
        //res.setHeader("Location", "/basic/hello-form.html");
        res.sendRedirect("/basic/hello-form.html");
    }
}
