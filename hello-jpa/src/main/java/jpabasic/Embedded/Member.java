package jpabasic.Embedded;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @Embedded
    private Address address1;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "2nd_city")),
            @AttributeOverride(name = "street", column = @Column(name = "2nd_street"))
    })
    private Address address2;
}
