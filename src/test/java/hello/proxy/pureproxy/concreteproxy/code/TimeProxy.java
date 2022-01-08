package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic{

    private ConcreteLogic target;

    public TimeProxy(ConcreteLogic target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();
        String result = target.operation();
        long endTime = System.currentTimeMillis();
        log.info("TimeDecorator 종료, result time = {}", (endTime - startTime));
        return result;
    }
}
