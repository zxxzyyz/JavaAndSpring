package Basic.MemberItemApp.dto;

import Basic.MemberItemApp.domain.Member;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class MemberDtoMapper {

    public Member toMember(MemberDto memberDto) {
        return new Member(null, memberDto.getUserName(), memberDto.getPassword(), new HashSet<>());
    }
}
