package Basic.MemberItemApp.service;

import Basic.MemberItemApp.domain.Item;
import Basic.MemberItemApp.domain.Member;
import Basic.MemberItemApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findByNamePassword(String userName, String password) {
        return memberRepository.findByUserNameEqualsAndPasswordEquals(userName, password);
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member(null, "a", "aa", null));
        memberRepository.save(new Member(null, "b", "bb", null));
    }
}
