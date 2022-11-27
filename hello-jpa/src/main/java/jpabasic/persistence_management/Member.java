package jpabasic.persistence_management;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
}
