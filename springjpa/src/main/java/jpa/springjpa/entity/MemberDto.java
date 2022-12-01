package jpa.springjpa.entity;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
