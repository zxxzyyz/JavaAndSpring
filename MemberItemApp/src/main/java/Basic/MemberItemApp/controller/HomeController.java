package Basic.MemberItemApp.controller;

import Basic.MemberItemApp.domain.Member;
import Basic.MemberItemApp.dto.MemberDto;
import Basic.MemberItemApp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

//    @GetMapping("/")
//    public String home(Model model) {
//        return "home";
//    }

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = "loginMember", required = false) Member member, Model model) {
        if (member == null) return "home";
        model.addAttribute("member", member);
        return "loginHome";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("memberDto") MemberDto memberDto) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("memberDto") MemberDto memberDto,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpServletRequest req) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = memberService.findByNamePassword(memberDto.getUserName(), memberDto.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "Wrong ID/Password.");
            return "login/loginForm";
        }

        // get session otherwise create new session
        HttpSession session = req.getSession(true);
        // store info into session
        session.setAttribute("loginMember", loginMember);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/";
    }
}
