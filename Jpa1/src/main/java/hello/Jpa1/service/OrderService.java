package hello.Jpa1.service;

import hello.Jpa1.domain.Delivery;
import hello.Jpa1.domain.Member;
import hello.Jpa1.domain.Order;
import hello.Jpa1.domain.OrderItem;
import hello.Jpa1.domain.item.Item;
import hello.Jpa1.repository.ItemRepository;
import hello.Jpa1.repository.MemberRepository;
import hello.Jpa1.repository.OrderRepository;
import hello.Jpa1.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // Retrieve entities
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // Delivery information
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // Create items for an order
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        // Create an order
        Order order = Order.createOrder(member, delivery, orderItem);

        // Save the order
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }
}
