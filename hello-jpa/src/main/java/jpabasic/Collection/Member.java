package jpabasic.Collection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @Embedded
    private Address homeAddress;

    // Create table table_name1 : member_id, food_name
    @ElementCollection
    @CollectionTable(name = "table_name1",
            joinColumns = @JoinColumn(name = "member_id")
    )
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    // Create table table_name2 : member_id, city, street
    @ElementCollection
    @CollectionTable(name = "table_name2",
            joinColumns = @JoinColumn(name = "member_id")
    )
    private List<Address> shippingAddress = new ArrayList<>();
}
