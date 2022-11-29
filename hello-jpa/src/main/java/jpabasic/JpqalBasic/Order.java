package jpabasic.JpqalBasic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    private int orderAmount;
    @Enumerated
    private Address address;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
