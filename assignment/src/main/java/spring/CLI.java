package spring;

import board.NoSuchBoardException;
import post.NoSuchPostException;

import java.net.MalformedURLException;
import java.util.Scanner;

public class CLI {
    private static final String COMMAND_PREFIX = "a ";

    private final ControllerMapper controllerMapper;
    private final Scanner scanner;

    private boolean exitFlag;

    public CLI(ControllerMapper controllerMapper) {
        this.controllerMapper = controllerMapper;
        scanner = new Scanner(System.in);
        exitFlag = false;
    }

    public void openWindow() {
        while (!exitFlag) {
            System.out.print(COMMAND_PREFIX);
            String command = scanner.nextLine();

            try {
                executeCommand(command);
            } catch (MalformedURLException | NoSuchBoardException | NoSuchPostException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("프로그램이 종료됩니다.");
    }

    private void executeCommand(String command) throws MalformedURLException {
        if (command.equals("exit") || command.equals("종료")) {
            exitFlag = true;
        }

        controllerMapper.call(command);
    }
}
