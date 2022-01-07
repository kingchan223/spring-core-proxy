package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.MessageDecorator;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import hello.proxy.pureproxy.decorator.code.TimeDecorator;
import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator(){
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(realComponent);

        decoratorPatternClient.execute();
    }

    @Test
    void decoratorTest1(){
        RealComponent realSubject = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realSubject);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(messageDecorator);

        decoratorPatternClient.execute();
    }

    @Test
    void decoratorTest2(){
        RealComponent realSubject = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realSubject);
        TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(timeDecorator);

        decoratorPatternClient.execute();
    }
}
