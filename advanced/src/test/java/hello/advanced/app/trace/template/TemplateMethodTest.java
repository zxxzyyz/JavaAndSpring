package hello.advanced.app.trace.template;

import hello.advanced.app.trace.template.code.AbstractTemplate;
import hello.advanced.app.trace.template.code.SubClassLogic1;
import hello.advanced.app.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        log.info("biz logic 2");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        log.info("biz logic 1");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate logic1 = new SubClassLogic1();
        logic1.execute();
        AbstractTemplate logic2 = new SubClassLogic2();
        logic2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate logic1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("biz logic 1");
            }
        };
        logic1.execute();

        AbstractTemplate logic2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("biz logic2");
            }
        };
        logic2.execute();
    }
}
