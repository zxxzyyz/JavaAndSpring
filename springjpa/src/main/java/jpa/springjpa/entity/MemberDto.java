package jpa.springjpa.entity;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;
}
