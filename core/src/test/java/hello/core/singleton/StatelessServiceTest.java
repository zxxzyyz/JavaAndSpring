package hello.core.singleton;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatelessServiceTest {
    @Test
    void statelessServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatelessService statelessService1 = ac.getBean(StatelessService.class);
        StatelessService statelessService2 = ac.getBean(StatelessService.class);

        // TheadA: userA order $10
        int priceUserA = statelessService1.order("userA", 10);
        // TheadB: userB order $20
        int priceUserB = statelessService2.order("userB", 20);

        Assertions.assertThat(priceUserA).isEqualTo(10);
        Assertions.assertThat(priceUserB).isEqualTo(20);
    }

    static class TestConfig {
        @Bean public StatelessService statelessService() {
            return new StatelessService();
        }
    }
}