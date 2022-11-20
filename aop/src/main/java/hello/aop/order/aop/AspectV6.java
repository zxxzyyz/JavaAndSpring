package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6 {

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before ::: from
            log.info("[transaction begin] {}", joinPoint.getSignature());
            // @Before ::: to

            Object result = joinPoint.proceed();

            // @AfterReturning ::: from
            log.info("[transaction commit] {}", joinPoint.getSignature());
            // @AfterReturning ::: to

            return result;
        } catch (Exception e) {
            // @AfterThrowing ::: from
            log.info("[transaction rollback] {}", joinPoint.getSignature());
            // @AfterThrowing ::: to

            throw e;
        } finally {
            // @After ::: from
            log.info("[release resources] {}", joinPoint.getSignature());
            // @After ::: to
        }
    }

    // around 1 -->> before 2 -->>
    // around 6 <<-- after 5 <<-- return 4 <<-- ex 3 <<--

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[doBefore] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("[doAfterReturning] result={} {}", result, joinPoint.getSignature());
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[doAfterThrowing] exception={} {}", ex, joinPoint.getSignature());
    }

    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[doAfter] {}", joinPoint.getSignature());
    }
}
