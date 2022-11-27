package jpabasic.O2M_M_O2M;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Product {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
}
