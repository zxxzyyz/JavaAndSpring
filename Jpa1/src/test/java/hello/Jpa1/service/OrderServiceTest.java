package hello.Jpa1.service;

import hello.Jpa1.domain.Address;
import hello.Jpa1.domain.Member;
import hello.Jpa1.domain.Order;
import hello.Jpa1.domain.OrderStatus;
import hello.Jpa1.domain.item.Book;
import hello.Jpa1.domain.item.Item;
import hello.Jpa1.exception.NotEnoughStockException;
import hello.Jpa1.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void order() {
        Member member = createMember();
        Book book = createBook("Koo's book", 10000, 100);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);
        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    void orderMoreThanStock() {
        Member member = createMember();
        Item item = createBook("Koo's book", 10000, 100);

        int orderCount = 101;
        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount)).isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void cancelOrder() {
        Member member = createMember();
        Item item = createBook("Koo's book", 10000, 100);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);
        Order order = orderRepository.findOne(orderId);

        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(100);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Koo");
        member.setAddress(new Address("Gifu", "Noritake", "111-9999"));
        em.persist(member);
        return member;
    }
}
