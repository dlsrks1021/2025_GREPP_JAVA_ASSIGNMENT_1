package board;

public class NoSuchBoardException extends RuntimeException {
    public NoSuchBoardException() {
    }

    public NoSuchBoardException(Long boardId) {
        super(boardId + "번 게시판은 존재하지 않습니다.");
    }

    public NoSuchBoardException(String boardName) {
        super(boardName + " 게시판은 존재하지 않습니다.");
    }

    public NoSuchBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBoardException(Throwable cause) {
        super(cause);
    }

    public NoSuchBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
