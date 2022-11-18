package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component {

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        long start = System.currentTimeMillis();

        String result = component.operation();

        long end = System.currentTimeMillis();
        long time = end - start;
        log.info("result time={}ms", time);

        return result;
    }
}
