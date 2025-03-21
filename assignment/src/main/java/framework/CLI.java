package framework;

import exception.NoSuchBoardException;
import lombok.Getter;
import exception.NoSuchPostException;

import java.net.MalformedURLException;
import java.util.Scanner;

public class CLI {
    private static final String COMMAND_PREFIX = "a ";
    private static final Scanner scanner = new Scanner(System.in);;
    @Getter private static final Session session = new Session();
    private static boolean exitFlag = false;

    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void printPrompt(String prompt) {
        System.out.println(prompt);
    }

    public static void openWindow(ControllerMapper controllerMapper) {
        while (!exitFlag) {
            String command = getUserInput(COMMAND_PREFIX);
            try {
                executeCommand(controllerMapper, command);
            } catch (MalformedURLException | NoSuchBoardException | NoSuchPostException e) {
                printPrompt(e.getMessage());
            }
        }

        printPrompt("프로그램이 종료됩니다.");
    }

    private static void executeCommand(ControllerMapper controllerMapper, String command) throws MalformedURLException {
        if (command.equals("exit") || command.equals("종료")) {
            exitFlag = true;
        }

        controllerMapper.call(command);
    }
}
