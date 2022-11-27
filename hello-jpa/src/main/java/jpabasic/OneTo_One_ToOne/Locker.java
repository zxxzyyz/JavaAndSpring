package jpabasic.OneTo_One_ToOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Locker {
    @Id @GeneratedValue
    @Column(name = "locker_id")
    private Long id;
    private String name;
    @OneToOne(mappedBy = "locker", fetch = FetchType.LAZY)
    private Member member;
}
