package hello.advanced.app.trace.strategy;

import hello.advanced.app.trace.strategy.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    @Test
    void callback() {
        TimeLogTemplate template = new TimeLogTemplate();

        template.execute(() -> log.info("biz logic1"));
        template.execute(() -> log.info("biz logic2"));
    }
}
