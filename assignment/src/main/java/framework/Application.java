package framework;

import java.util.Scanner;

public class Application {
    private static final String DOMAIN = "https://www.kckim.com";
    private final BeanFactory beanFactory;
    private final ControllerMapper controllerMapper;
    private final Scanner scanner = new Scanner(System.in);


    public Application() {
        beanFactory = new BeanFactory();
        controllerMapper = new ControllerMapper(beanFactory);
    }

    public void run() {
        Session session = new Session();
        while (true) {
            System.out.print(DOMAIN);
            String userInput = scanner.nextLine().trim();
            if (userInput.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            try {
                Request request = new Request(new URL(userInput), session);
                controllerMapper.call(request);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
