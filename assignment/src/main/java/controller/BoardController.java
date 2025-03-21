package controller;

import service.BoardService;
import exception.NoSuchBoardException;
import lombok.RequiredArgsConstructor;
import framework.CLI;
import model.Board;
import framework.annotation.Controller;
import framework.annotation.GetMapping;
import framework.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/add")
    public void add() {
        String boardName = CLI.getUserInput("새로운 게시판 이름을 입력해주세요 > ");
        String author = CLI.getSession().isUserLoggedIn() ?
                CLI.getSession().getAccount().getAccountName() : "비회원";
        boardService.createBoard(boardName, author);
        CLI.printPrompt("게시판이 작성되었습니다.");
    }

    @GetMapping("/boards/edit")
    public void edit(@RequestParam("boardId") long boardId) {
        if (!boardService.isBoardExist(boardId)) {
            throw new NoSuchBoardException(boardId);
        }
        String boardName = CLI.getUserInput("게시판을 수정합니다.\n변경할 이름을 입력하세요 > ");
        boardService.editBoard(boardId, boardName);
        CLI.printPrompt("게시판이 수정되었습니다.");
    }

    @GetMapping("/boards/remove")
    public void remove(@RequestParam("boardId") long boardId) {
        Board board = boardService.deleteBoard(boardId);
        CLI.printPrompt(board.getBoardName() + " 게시판이 삭제되었습니다.");
    }

    @GetMapping("/boards/view")
    public void view(@RequestParam("boardName") String boardName) {
        CLI.printPrompt(boardService.getBoardInfo(boardName));
    }
}
