package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void case1() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername("정상");

        orderService.order(order);

        Order foundOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(foundOrder.getPayStatus()).isEqualTo("완료");
    }

    @Test
    void case2() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername("예외");

        Assertions.assertThatThrownBy(() -> orderService.order(order)).isInstanceOf(RuntimeException.class);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        Assertions.assertThat(foundOrder.isEmpty()).isTrue();
    }

    @Test
    void case3() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername("잔고부족");

        Assertions.assertThatThrownBy(() -> orderService.order(order)).isInstanceOf(NotEnoughMoneyException.class);

        Order foundOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(foundOrder.getPayStatus()).isEqualTo("대기");
    }
}