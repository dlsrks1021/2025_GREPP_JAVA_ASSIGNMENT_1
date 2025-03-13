import java.util.Arrays;
import java.util.Scanner;

public class CLI {
    private static final String PREFIX = "명령어 > ";
    private static final String[] EXIT_KEYWORDS = {"exit", "종료"};
    private static final Scanner scanner = new Scanner(System.in);

    public static void openWindow() {
        while (true) {
            System.out.print(PREFIX);
            String input = scanner.nextLine();

            if (Arrays.asList(EXIT_KEYWORDS).contains(input)){
                System.out.println("프로그램이 종료됩니다.");
                break;
            } else {
                System.out.println("존재하지 않는 명령어 입니다.");
            }
        }
    }
}
