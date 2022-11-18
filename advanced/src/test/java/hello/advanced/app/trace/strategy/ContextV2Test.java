package hello.advanced.app.trace.strategy;

import hello.advanced.app.trace.strategy.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    void strategyV1() {
        ContextV2 context1 = new ContextV2();
        context1.execute(new StrategyLogic1());
        context1.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        ContextV2 context1 = new ContextV2();
        context1.execute(new Strategy() {
            @Override
            public void call() {
                log.info("biz logic1");
            }
        });
        context1.execute(new Strategy() {
            @Override
            public void call() {
                log.info("biz logic2");
            }
        });
    }

    @Test
    void strategyV3() {
        ContextV2 context1 = new ContextV2();
        context1.execute(() -> log.info("biz logic1"));
        context1.execute(() -> log.info("biz logic2"));
    }
}
