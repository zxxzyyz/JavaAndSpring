package Basic.MemberItemApp.controller;

import Basic.MemberItemApp.domain.Member;
import Basic.MemberItemApp.dto.MemberDto;
import Basic.MemberItemApp.dto.MemberDtoMapper;
import Basic.MemberItemApp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberDtoMapper memberDtoMapper;

    @GetMapping("/create")
    public String createForm(@ModelAttribute("member") MemberDto memberDto) {
        return "/members/memberCreateForm";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/members/memberCreateForm";
        }

        Member member = memberDtoMapper.toMember(memberDto);
        memberService.save(member);

        return "redirect:/";
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Member members(@Valid @RequestBody MemberDto memberDto) {
        Member member = memberDtoMapper.toMember(memberDto);
        Member savedMember = memberService.save(member);
        return savedMember;
    }

}
