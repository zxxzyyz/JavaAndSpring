package jpabasic.OneToOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.concurrent.locks.Lock;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id")
    private Locker locker;
}
