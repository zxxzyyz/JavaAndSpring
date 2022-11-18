package hello.advanced;

import hello.advanced.app.trace.logtrace.FieldLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    FieldLogTrace fieldLogTrace() {
        return new FieldLogTrace();
    }
}
