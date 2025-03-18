package board;

import spring.annotation.Repository;

import java.util.*;

@Repository
public class BoardRepository {

    private Long sequence = 0L;
    private final Map<Long, Board> boardMap = new HashMap<>();

    public void save(Board board) {
        board.setBoardId(++sequence);
        boardMap.put(board.getBoardId(), board);
    }

    public Board delete(long boardId) {
        return boardMap.remove(boardId);
    }

    public Board findById(Long id) {
        return boardMap.get(id);
    }

    public Board findByName(String name) {
        return boardMap.values().stream()
                .filter(b -> b.getBoardName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchBoardException(name));
    }

    public List<Board> findAll() {
        return new ArrayList<>(boardMap.values());
    }

    public void update(Board newBoard) {
        Board oldBoard = boardMap.get(newBoard.getBoardId());
        if (oldBoard == null) {
            throw new NoSuchBoardException(newBoard.getBoardId());
        }
        oldBoard.setBoardName(newBoard.getBoardName());
    }
}
