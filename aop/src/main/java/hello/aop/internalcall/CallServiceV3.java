package hello.aop.internalcall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final ObjectProvider<CallServiceV3> callServiceV3ObjectProvider;

    public void external() {
        log.info("call external");
        CallServiceV3 callServiceV3 = callServiceV3ObjectProvider.getObject();
        callServiceV3.internal();
    }
    public void internal() {
        log.info("call internal");
    }
}