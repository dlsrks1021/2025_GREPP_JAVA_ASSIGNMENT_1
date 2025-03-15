package post;

import lombok.Getter;

@Getter
public class NoSuchPostException extends RuntimeException{

    public NoSuchPostException() {
    }

    public NoSuchPostException(Long postId) {
        super(postId + "번 게시글은 존재하지 않습니다.");
    }

    public NoSuchPostException(String message) {
        super(message);
    }

    public NoSuchPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPostException(Throwable cause) {
        super(cause);
    }

    public NoSuchPostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
