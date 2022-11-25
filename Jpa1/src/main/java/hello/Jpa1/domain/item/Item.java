package hello.Jpa1.domain.item;

import hello.Jpa1.domain.Category;
import hello.Jpa1.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int leftStock = this.stockQuantity - quantity;
        if (leftStock < 0) {
            throw new NotEnoughStockException("No enough stocks in inventory");
        }
        this.stockQuantity = leftStock;
    }
}
