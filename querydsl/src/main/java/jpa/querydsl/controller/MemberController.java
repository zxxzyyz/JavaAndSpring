package jpa.querydsl.controller;

import jpa.querydsl.entity.Member;
import jpa.querydsl.entity.MemberSearchCond;
import jpa.querydsl.entity.MemberTeamDto;
import jpa.querydsl.repository.MemberJpaRepository;
import jpa.querydsl.repository.MemberRepository;
import jpa.querydsl.repository.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCond cond) {
        return memberJpaRepository.search(cond);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCond cond, Pageable pageable) {
        return memberRepository.searchSimple(cond, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCond cond, Pageable pageable) {
        return memberRepository.searchComplex(cond, pageable);
    }
}
