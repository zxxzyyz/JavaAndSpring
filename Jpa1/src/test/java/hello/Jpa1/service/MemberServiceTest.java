package hello.Jpa1.service;

import hello.Jpa1.domain.Member;
import hello.Jpa1.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void signup() {
        Member member = new Member();
        member.setName("koo");

        Long savedId = memberService.join(member);

        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    void signup2() {
        Member member1 = new Member();
        member1.setName("koo");

        Member member2 = new Member();
        member2.setName("koo");

        Long savedId = memberService.join(member1);
        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    }
}