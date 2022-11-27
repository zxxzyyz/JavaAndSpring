package jpabasic.ManyToMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name = "member_product") // This will create member_product table that has keys for members and products
    private List<Product> products = new ArrayList<>();
}
