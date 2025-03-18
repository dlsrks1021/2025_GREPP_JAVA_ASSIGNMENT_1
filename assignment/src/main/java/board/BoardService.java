package board;

import lombok.RequiredArgsConstructor;
import post.Post;
import spring.annotation.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void createBoard(String boardName) {
        Board board = new Board(boardName);
        boardRepository.save(board);
    }

    public String getBoardInfo(String boardName) {
        Board board = boardRepository.findByName(boardName);
        StringBuilder boardInfo = new StringBuilder();
        boardInfo.append("[" + board.getBoardId() + "][" + board.getBoardName() + " 게시판]\n");
        board.getPosts().add(new Post("post", "content"));
        board.getPosts().add(new Post("post2", "content2"));
        board.getPosts().forEach(boardInfo::append);

        return boardInfo.toString();
    }

    public boolean isBoardExist(long boardId) {
        return boardRepository.findById(boardId) != null;
    }

    public void editBoard(Long boardId, String boardName) {
        Board board = new Board(boardId, boardName);
        boardRepository.update(board);
    }

    public Board deleteBoard(Long boardId) {
        Board result = boardRepository.delete(boardId);
        if (result == null) {
            throw new NoSuchBoardException(boardId);
        }
        return result;
    }
}
