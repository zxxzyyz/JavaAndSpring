package jpa.springjpa.controller;

import jpa.springjpa.entity.Member;
import jpa.springjpa.entity.MemberDto;
import jpa.springjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    /*
    파라미터로 Pageable 을 받을 수 있다.
    Pageable 은 인터페이스, 실제는 org.springframework.data.domain.PageRequest 객체 생성
    요청 파라미터
    예) /members?page=0&size=3&sort=id,desc&sort=username,desc
    page: 현재 페이지, 0부터 시작한다.
    size: 한 페이지에 노출할 데이터 건수
    sort: 정렬 조건을 정의한다. 예) 정렬 속성,정렬 속성...(ASC | DESC), 정렬 방향을 변경하고 싶으면 sort
    파라미터 추가 ( asc 생략 가능)
    */
    @GetMapping("/members")
    public Page<Member> paging1(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    /*
    페이징 정보가 둘 이상이면 접두사로 구분
    @Qualifier 에 접두사명 추가 "{접두사명}_xxx”
    예제: /members?member_page=0&order_page=1
    @Qualifier("member") Pageable memberPageable
    @Qualifier("order") Pageable orderPageable
    */
    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public Page<Member> paging2(@PageableDefault(size = 12, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members")
    public Page<MemberDto> paging3(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> pageDto = page.map(member -> new MemberDto(member));
        return pageDto;
        //return memberRepository.findAll(pageable).map(MemberDto::new);
    }
    /*
    Page를 1부터 시작하기
    스프링 데이터는 Page를 0부터 시작한다. 만약 1부터 시작하려면?
    1. Pageable, Page를 파리미터와 응답 값으로 사용히지 않고, 직접 클래스를 만들어서 처리한다. 그리고
    직접 PageRequest(Pageable 구현체)를 생성해서 리포지토리에 넘긴다. 물론 응답값도 Page 대신에 직접 만들어서 제공해야 한다.
    2. spring.data.web.pageable.one-indexed-parameters 를 true 로 설정한다. 그런데 이 방법은
    web에서 page 파라미터를 -1 처리 할 뿐이다. 따라서 응답값인 Page 에 모두 0 페이지 인덱스를 사용하는 한계가 있다.
    */
}
