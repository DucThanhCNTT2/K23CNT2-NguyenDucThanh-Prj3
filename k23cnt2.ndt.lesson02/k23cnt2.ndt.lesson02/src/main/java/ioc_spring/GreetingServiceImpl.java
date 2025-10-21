package ioc_spring;

import org.springframework.stereotype.Service;

@Service  // => Spring sẽ tự quản lý Bean này
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet(String name) {
        return "Xin chào " + name + " (từ Spring IoC Service)";
    }
}
