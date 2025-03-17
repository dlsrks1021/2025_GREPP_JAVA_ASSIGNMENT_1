package spring;

public class SpringBootApplication {
    private final BeanFactory beanFactory;
    private final ControllerMapper controllerMapper;
    private final CLI cli;

    public SpringBootApplication() {
        beanFactory = new BeanFactory();
        controllerMapper = new ControllerMapper(beanFactory);
        cli = new CLI(controllerMapper);
    }

    public void run() {
        cli.openWindow();
    }
}
