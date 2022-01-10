package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;//aopalliance 패키지의 MethodInterceptor 사용
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    //MethodInvocation에 target 클래스 정보가 담겨있다.
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("Time Proxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = invocation.proceed();//invocation.proceed()를 호출하면 알아서 target을 찾아 호출해준다.

        long endTime = System.currentTimeMillis();
        log.info("TimeProxy 종료 resultTime = {}", endTime-startTime);
        return result;
    }
}
