package post;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
public class Post {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Timestamp postTime;

    public Post(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTime = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return postId + " / " + postTitle + " / " + postTime + "\n";
    }
}