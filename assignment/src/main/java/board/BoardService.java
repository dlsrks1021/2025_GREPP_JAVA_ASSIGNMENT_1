package board;

import lombok.RequiredArgsConstructor;
import post.PostRepository;
import spring.annotation.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public void createBoard(String boardName) {
        Board board = new Board(boardName);
        boardRepository.save(board);
    }

    public String getBoardInfo(String boardName) {
        Board board = boardRepository.findByName(boardName);

        StringBuilder boardInfo = new StringBuilder();
        boardInfo.append("[" + board.getBoardId() + "][" + board.getBoardName() + " 게시판]\n");
        postRepository.findAll().stream()
                .filter(p -> p.getBoardId().equals(board.getBoardId()))
                .forEach(boardInfo::append);

        return boardInfo.toString();
    }

    public boolean isBoardExist(long boardId) {
        return boardRepository.findById(boardId) != null;
    }

    public void editBoard(long boardId, String boardName) {
        Board board = new Board(boardId, boardName);
        boardRepository.update(board);
    }

    public Board deleteBoard(long boardId) {
        Board result = boardRepository.delete(boardId);
        if (result == null) {
            throw new NoSuchBoardException(boardId);
        }
        postRepository.findAll().stream()
                .filter(p -> p.getBoardId().equals(boardId))
                .forEach(p -> postRepository.delete(p.getPostId()));
        return result;
    }
}
