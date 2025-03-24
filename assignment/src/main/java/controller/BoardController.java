package controller;

import framework.Session;
import service.BoardService;
import exception.NoSuchBoardException;
import lombok.RequiredArgsConstructor;
import model.Board;
import framework.annotation.Controller;
import framework.annotation.GetMapping;
import framework.annotation.RequestParam;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final Scanner scanner;
    @GetMapping("/boards/add")
    public void add(Session session) {
        System.out.println("새로운 게시판 이름을 입력해주세요 > ");
        String boardName = scanner.nextLine();
        String userName = (String) session.getAttribute("userName");
        String author = userName != null ? userName : "비회원";
        boardService.createBoard(boardName, author);
        System.out.println("게시판이 작성되었습니다.");
    }

    @GetMapping("/boards/edit")
    public void edit(@RequestParam("boardId") long boardId) {
        if (!boardService.isBoardExist(boardId)) {
            throw new NoSuchBoardException(boardId);
        }
        System.out.println("게시판을 수정합니다.");
        System.out.print("변경할 이름을 입력하세요 > ");
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
