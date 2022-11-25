package hello.Jpa1;

import hello.Jpa1.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void testMember() {
        Member member = new Member();
        member.setUsername("memberA");

        Long savedId = memberRepository.save(member);
        Member foundMember = memberRepository.find(savedId);

        Assertions.assertThat(member).isEqualTo(foundMember);
    }
}