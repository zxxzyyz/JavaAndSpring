package jpa.querydsl.entity;

import lombok.Data;

@Data
public class MemberSearchCond {
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}
