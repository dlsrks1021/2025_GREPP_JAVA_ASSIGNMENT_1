package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
public class Post {
    private Long postId;
    private Long boardId;
    private String postTitle;
    private String postContent;
    private Timestamp postTime;
    private Timestamp editedTime;
    private String author;

    public Post(Long boardId, String postTitle, String postContent) {
        this.boardId = boardId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTime = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return postId + " / " + postTitle + " / " + postTime + "\n";
    }
}