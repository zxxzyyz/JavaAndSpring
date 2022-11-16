package hello.springtx.order;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String username; //정산, 예외, 잔고부족
    private String payStatus; //대기, 완료

    public void setUsername(String username) {
        this.username = username;
    }

    public void paymentInProgress() {
        payStatus = "대기";
    }

    public void paymentComplete() {
        payStatus = "완료";
    }
}
