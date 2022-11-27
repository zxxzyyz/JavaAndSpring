package jpabasic.ManyToMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Product {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String name;
}
