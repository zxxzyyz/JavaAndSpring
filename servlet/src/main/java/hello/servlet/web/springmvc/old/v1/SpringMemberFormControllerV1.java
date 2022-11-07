package hello.servlet.web.springmvc.old.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// Ways to add into handler mapping
// 1. Add Controller annotation to class
// 2. Add Component and RequestMapping annotation to class
// 3. Add RequestMapping annotation to class and manually add the class to Bean
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
