package hello.aop.internalcall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {
    private final ApplicationContext applicationContext;

    public void external() {
        log.info("call external");
        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        callServiceV2.internal();
    }
    public void internal() {
        log.info("call internal");
    }
}