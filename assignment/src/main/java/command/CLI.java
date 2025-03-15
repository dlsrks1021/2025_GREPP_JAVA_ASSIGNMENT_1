package command;

import post.PostRepository;
import post.PostService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {
    private static final String COMMAND_PREFIX = "명령어 > ";

    private final PostService postService;
    private final Scanner scanner;

    private boolean exitFlag;

    public CLI() {
        postService = new PostService(new PostRepository());
        scanner = new Scanner(System.in);
        exitFlag = false;
    }

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

    private void executeCommand(String command) throws NoSuchCommandException {
        switch (command) {
            case "exit", "종료" -> exitFlag = true;
            case "작성" -> write();
            case "조회" -> read();
            case "삭제" -> delete();
            case "수정" -> update();
            default ->
                throw new NoSuchCommandException("존재하지 않는 명령어 입니다.");
        }
    }

    private void write() {
        System.out.print("글의 제목을 입력하세요 > ");
        String postName = scanner.nextLine();
        System.out.print("글의 내용을 입력하세요 > ");
        String postContent = scanner.nextLine();

        postService.writePost(postName, postContent);

        System.out.println("글을 작성했습니다!");
    }

    private void read() {
        System.out.print("어떤 게시물을 조회할까요? > ");
        try {
            Long postId = scanner.nextLong();
            scanner.nextLine(); // Flush Buffer
            System.out.println(postService.readPost(postId));
        } catch (InputMismatchException e) {
            System.out.println("게시물 번호를 입력해주세요!");
        }
    }

    private void delete() {
        System.out.print("어떤 게시물을 삭제할까요? > ");
        try {
            Long postId = scanner.nextLong();
            scanner.nextLine(); // Flush Buffer
            postService.deletePost(postId);
            System.out.println(postId + "번 게시물이 성공적으로 삭제되었습니다!");
        } catch (InputMismatchException e) {
            System.out.println("게시물 번호를 입력해주세요!");
        }
    }

    private void update() {
        System.out.println("어떤 게시물을 수정할까요? > ");
        try {
            Long postId = scanner.nextLong();
            scanner.nextLine(); // Flush Buffer
            System.out.println(postId + "번 게시물을 수정합니다.");

            System.out.print("글의 제목을 입력하세요 > ");
            String postName = scanner.nextLine();
            System.out.print("글의 내용을 입력하세요 > ");
            String postContent = scanner.nextLine();

            postService.editPost(postId, postName, postContent);

            System.out.println(postId + "번 게시물이 성공적으로 수정되었습니다!");
        } catch (InputMismatchException e) {
            System.out.println("게시물 번호를 입력해주세요!");
        }
    }

}
