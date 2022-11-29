package hello.Jpa1.api;

import hello.Jpa1.domain.Order;
import hello.Jpa1.repository.OrderRepository;
import hello.Jpa1.repository.OrderSearch;
import hello.Jpa1.repository.order.simplequery.OrderSimpleQueryDto;
import hello.Jpa1.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ManyToOne, OneToOne
 * Order
 * Order -> Member(ManyToOne)
 * Order -> Delivery(OneToOne)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); // Force to load data for LAZY fetch type
            order.getDelivery().getAddress(); // Force to load data for LAZY fetch type
        }
        return orders;
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    @GetMapping("/api/v2/simple-orders")
    public Result orderV2() {
//        List<Order> orders = orderRepository.findAll(new OrderSearch());
//        List<SimpleOrderDto> collect = orders.stream().map(order -> new SimpleOrderDto(order)).collect(Collectors.toList());
//        return new Result(collect.size(), collect);

//        List<SimpleOrderDto> collect = orderRepository.findAll(new OrderSearch()).stream().map(order -> new SimpleOrderDto(order)).collect(Collectors.toList());
//        return new Result(collect.size(), collect);

        List<OrderSimpleQueryDto> collect = orderRepository.findAll(new OrderSearch()).stream().map(OrderSimpleQueryDto::new).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     */
    @GetMapping("/api/v3/simple-orders")
    public Result orderV3() {
        List<OrderSimpleQueryDto> collect = orderRepository.findAllFetchMemberDelivery(new OrderSearch()).stream().map(OrderSimpleQueryDto::new).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    /**
     * V4. JPA에서 DTO로 바로 조회
     * - 쿼리 1번 호출
     * - select 절에서 원하는 데이터만 선택해서 조회
     */
    @GetMapping("/api/v4/simple-orders")
    public Result orderV4() {
        List<OrderSimpleQueryDto> collect = orderSimpleQueryRepository.findOrderDtos();
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int count;
        private T data;
    }
}
