package hello.proxy.advisor;

import hello.proxy.common.ServiceImpl;
import hello.proxy.common.ServiceInterface;
import hello.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {
    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // --- NameMatchMethodPointcut --
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");//메서드 이름이 save인 경우에만 어드바이스 적용
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        // -------------------------------
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut{

        @Override//클래스는 언제나 true
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override//메서드는 직접 구현한 MyMethodMatcher에 따라
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }

    }
    static class MyMethodMatcher implements MethodMatcher{
        private final String matchName = "save";
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트 컷 호출 method = {}, targetClass = {}", method.getName(), targetClass);
            log.info("포인트 컷의 결과 = {}", result);
            return result;
        }
        @Override
        public boolean isRuntime() {
            return false;
        }
        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
