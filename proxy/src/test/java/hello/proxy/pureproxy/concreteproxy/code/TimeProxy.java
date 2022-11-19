package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator begin");
        long start = System.currentTimeMillis();

        String result = concreteLogic.operation();

        long end = System.currentTimeMillis();
        long time = end - start;
        log.info("TimeDecorator end time={}ms", time);

        return result;
    }
}
