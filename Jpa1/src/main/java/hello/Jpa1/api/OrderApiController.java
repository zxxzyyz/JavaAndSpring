package hello.Jpa1.api;

import hello.Jpa1.domain.Address;
import hello.Jpa1.domain.Order;
import hello.Jpa1.domain.OrderItem;
import hello.Jpa1.domain.OrderStatus;
import hello.Jpa1.repository.OrderRepository;
import hello.Jpa1.repository.OrderSearch;
import hello.Jpa1.repository.order.query.OrderFlatDto;
import hello.Jpa1.repository.order.query.OrderQueryDto;
import hello.Jpa1.repository.order.query.OrderQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public Result ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> items = order.getOrderItems();
            items.stream().forEach(i -> i.getItem().getName());
        }
        return new Result(orders.size(), orders);
    }

    @GetMapping("/api/v2/orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @GetMapping("/api/v3/orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllFetchItem();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @GetMapping("/api/v3.1/orders")
    public Result ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<Order> orders = orderRepository.findAllFetchMemberDelivery(offset, limit);
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @GetMapping("/api/v4/orders")
    public Result ordersV4() {
        List<OrderQueryDto> result = orderQueryRepository.findOrderQueryDtos();
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v5/orders")
    public Result ordersV5() {
        List<OrderQueryDto> result = orderQueryRepository.findAllByDtoOptimized();
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v6/orders")
    public Result ordersV6() {
        List<OrderFlatDto> result = orderQueryRepository.findAllByDtoFlat();
        return new Result(result.size(), result);
    }

    @Data
    private static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }
    }

    @Data
    private static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int count;
        private T data;
    }
}
