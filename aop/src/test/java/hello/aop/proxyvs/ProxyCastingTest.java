package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(false); //jdk

        // proxy to interface
        MemberService memberServiceProxy = (MemberService) factory.getProxy();

        // interface to proxy : fail
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(true); //cglib

        // proxy to interface
        MemberService memberServiceProxy = (MemberService) factory.getProxy();

        // interface to proxy : success
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
