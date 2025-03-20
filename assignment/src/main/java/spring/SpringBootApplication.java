package spring;

import cli.CLI;

public class SpringBootApplication {
    private final BeanFactory beanFactory;
    private final ControllerMapper controllerMapper;

    public SpringBootApplication() {
        beanFactory = new BeanFactory();
        controllerMapper = new ControllerMapper(beanFactory);
    }

    public void run() {
        CLI.openWindow(controllerMapper);
    }
}
