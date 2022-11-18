package hello.advanced.app.trace.strategy;

import hello.advanced.app.trace.strategy.code.ContextV1;
import hello.advanced.app.trace.strategy.code.Strategy;
import hello.advanced.app.trace.strategy.code.StrategyLogic1;
import hello.advanced.app.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV1() {
        Strategy logic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(logic1);
        log.info("logic1={}", logic1.getClass());
        context1.execute();

        Strategy logic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(logic2);
        log.info("logic2={}", logic2.getClass());
        context2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("biz logic 1");
            }
        };
        log.info("logic1={}", strategyLogic1.getClass());
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();
        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("biz logic 2");
            }
        };
        log.info("logic2={}", strategyLogic2.getClass());
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("biz logic 1");
                log.info("logic1={}", this.getClass());
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("biz logic 2");
                log.info("logic2={}", this.getClass());
            }
        });
        context2.execute();
    }

    @Test
    void strategyV4() {
        ContextV1 context1 = new ContextV1(() -> log.info("biz logic 1"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("biz logic 2"));
        context2.execute();
    }
}
