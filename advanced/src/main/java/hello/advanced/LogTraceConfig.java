package hello.advanced;

import hello.advanced.app.trace.logtrace.FieldLogTrace;
import hello.advanced.app.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    ThreadLocalLogTrace threadLocalLogTrace() {
        return new ThreadLocalLogTrace();
    }
}
