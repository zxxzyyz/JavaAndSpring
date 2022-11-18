package hello.advanced.app.trace.strategy.code;

import hello.advanced.app.trace.strategy.code.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyLogic1 implements Strategy {
    @Override
    public void call() {
        log.info("biz logic1");
    }
}
