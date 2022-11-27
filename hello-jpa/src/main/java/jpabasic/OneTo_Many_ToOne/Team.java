package jpabasic.OneTo_Many_ToOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;
    @OneToMany
    @JoinColumn(name = "team_id")
    List<Member> members = new ArrayList<Member>();
}
