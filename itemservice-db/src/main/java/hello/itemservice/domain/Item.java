package hello.itemservice.domain;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.lang.annotation.Target;

@Data
@Entity
//@Table(name = "item") 객체명이랑 동일시 생략가능
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 스프링 JPA 사용시 카멜 스타일을 언더스코어 스타일로 자동 변환 해주기 때문에 아래의 name도 생략 가능
    @Column(name = "item_name", length = 10)
    private String itemName;

    //@Column(name = "price") 필드명이랑 동일시 생략가능
    private Integer price;
    private Integer quantity;

    // JPA는 public/protected 기본 생성자 필수
    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
