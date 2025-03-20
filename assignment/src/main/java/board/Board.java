package board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Board {
    private long boardId;
    private String boardName;
    private String author;

    public Board(String boardName) {
        this.boardName = boardName;
    }

    public Board(long boardId, String boardName) {
        this.boardId = boardId;
        this.boardName = boardName;
    }
}
