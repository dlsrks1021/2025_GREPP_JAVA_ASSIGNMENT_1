import java.util.Scanner;

public class CLI {
    private static final String COMMAND_PREFIX = "명령어 > ";

    private final Scanner scanner = new Scanner(System.in);
    private boolean exitFlag = false;

    public void openWindow() {
        while (!exitFlag) {
            System.out.print(COMMAND_PREFIX);
            String command = scanner.nextLine();

            try {
                executeCommand(command);
            } catch (NoSuchCommandException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("프로그램이 종료됩니다.");
    }

    private void executeCommand(String command) {
        switch (command) {
            case "exit":
            case "종료" :
                exitFlag = true;
                break;
            default:
                throw new NoSuchCommandException("존재하지 않는 명령어 입니다.");
        }
    }
}
