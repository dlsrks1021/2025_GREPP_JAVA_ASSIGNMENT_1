package spring;

import board.NoSuchBoardException;
import post.NoSuchPostException;

import java.net.MalformedURLException;
import java.util.Scanner;

public class CLI {
    private static final String COMMAND_PREFIX = "a ";
    private static final Scanner scanner = new Scanner(System.in);;
    private final ControllerMapper controllerMapper;
    private boolean exitFlag;

    public CLI(ControllerMapper controllerMapper) {
        this.controllerMapper = controllerMapper;
        exitFlag = false;
    }

    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void printPrompt(String prompt) {
        System.out.println(prompt);
    }

    public void openWindow() {
        while (!exitFlag) {
            String command = getUserInput(COMMAND_PREFIX);
            try {
                executeCommand(command);
            } catch (MalformedURLException | NoSuchBoardException | NoSuchPostException e) {
                printPrompt(e.getMessage());
            }
        }

        printPrompt("프로그램이 종료됩니다.");
    }

    private void executeCommand(String command) throws MalformedURLException {
        if (command.equals("exit") || command.equals("종료")) {
            exitFlag = true;
        }

        controllerMapper.call(command);
    }
}
