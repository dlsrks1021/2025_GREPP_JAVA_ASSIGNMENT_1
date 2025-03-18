package board;

import lombok.RequiredArgsConstructor;
import spring.annotation.Controller;
import spring.annotation.GetMapping;
import spring.annotation.RequestParam;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/add")
    public void add() {
        System.out.println("새로운 게시판 이름을 입력해주세요 > ");
        Scanner scanner = new Scanner(System.in);
        String boardName = scanner.nextLine();
        boardService.createBoard(boardName);
        System.out.println("게시판이 작성되었습니다.");
    }

    @GetMapping("/boards/edit")
    public void edit(@RequestParam("boardId") long boardId) {
        if (!boardService.isBoardExist(boardId)) {
            throw new NoSuchBoardException(boardId);
        }
        System.out.println("게시판 이름을 수정합니다 > ");
        Scanner scanner = new Scanner(System.in);
        String boardName = scanner.nextLine();
        boardService.editBoard(boardId, boardName);
        System.out.println("게시판이 수정되었습니다.");
    }

    @GetMapping("/boards/remove")
    public void remove(@RequestParam("boardId") long boardId) {
        Board board = boardService.deleteBoard(boardId);
        System.out.println(board.getBoardName() + " 게시판이 삭제되었습니다.");
    }

    @GetMapping("/boards/view")
    public void view(@RequestParam("boardName") String boardName) {
        System.out.println(boardService.getBoardInfo(boardName));
    }
}
