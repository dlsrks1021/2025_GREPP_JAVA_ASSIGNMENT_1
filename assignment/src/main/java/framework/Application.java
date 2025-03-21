package framework;

public class Application {
    private final BeanFactory beanFactory;
    private final ControllerMapper controllerMapper;

    public Application() {
        beanFactory = new BeanFactory();
        controllerMapper = new ControllerMapper(beanFactory);
    }

    public void run() {
        CLI.openWindow(controllerMapper);
    }
}
