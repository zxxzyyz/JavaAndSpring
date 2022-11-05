package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void join() {
        // given
        Member m = new Member();
        m.setName("m1");

        // when
        Long mId = memberService.join(m);

        // then
        Member findMember = memberService.findOne(mId).get();
        assertThat(m.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void duplicateJoin() {
        // given
        Member m1 = new Member();
        m1.setName("m1");
        Member m2 = new Member();
        m2.setName("m1");

        // when
        memberService.join(m1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            memberService.join(m2);
        });

        assertThat(e.getMessage()).isEqualTo("Existing member");
    }
}