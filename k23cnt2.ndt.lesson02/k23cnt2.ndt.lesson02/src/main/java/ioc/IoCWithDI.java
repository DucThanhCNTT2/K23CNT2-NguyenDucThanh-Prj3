package ioc;

class IoCService {
    public void serve() {
        System.out.println("Service is serving");
    }
}

class IoCClient {
    private IoCService iocService;

    // Dùng DI: truyền Service vào thay vì tự tạo
    public IoCClient(IoCService service) {
        this.iocService = service;
    }

    public void doSomething() {
        iocService.serve();
    }
}

public class IoCWithDI {
    public static void main(String[] args) {
        // Tạo đối tượng Service
        IoCService service = new IoCService();

        // Tiêm (inject) vào Client
        IoCClient client = new IoCClient(service);
        client.doSomething();
    }
}
