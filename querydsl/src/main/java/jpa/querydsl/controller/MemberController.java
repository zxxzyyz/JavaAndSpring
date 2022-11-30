package jpa.querydsl.controller;

import jpa.querydsl.entity.Member;
import jpa.querydsl.entity.MemberSearchCond;
import jpa.querydsl.entity.MemberTeamDto;
import jpa.querydsl.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    private List<MemberTeamDto> searchMemberV1(MemberSearchCond cond) {
        return memberJpaRepository.search(cond);
    }
}
