import java.util.Scanner;

public class CLI {
    private static final String PREFIX = "명령어 > ";
    private static final Scanner scanner = new Scanner(System.in);

    public static void openWindow() {
        while (true) {
            System.out.print(PREFIX);
            String input = scanner.nextLine();
            System.out.println(input);
        }
    }
}
