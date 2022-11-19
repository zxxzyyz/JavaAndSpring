package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.*;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean(name = "ConcreteController")
    OrderControllerV2 orderController(LogTrace logTrace) {
        OrderControllerV2 controllerImpl = new OrderControllerV2(orderService(logTrace));
        return new OrderControllerConcreteProxy(controllerImpl, logTrace);
    }

    @Bean(name = "ConcreteService")
    OrderServiceV2 orderService(LogTrace logTrace) {
        OrderServiceV2 serviceImpl = new OrderServiceV2(orderRepository(logTrace));
        return new OrderServiceConcreteProxy(serviceImpl, logTrace);
    }

    @Bean(name = "ConcreteRepository")
    OrderRepositoryV2 orderRepository(LogTrace logTrace) {
        OrderRepositoryV2 repositoryImpl = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(repositoryImpl, logTrace);
    }
}
