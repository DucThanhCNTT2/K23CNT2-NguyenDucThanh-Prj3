package ioc_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component  // => Cũng là 1 Spring Bean
public class GreetingController {

    // Spring sẽ tự inject GreetingServiceImpl vào đây
    @Autowired
    private GreetingService greetingService;

    public void sayHello(String name) {
        System.out.println(greetingService.greet(name));
    }
}
