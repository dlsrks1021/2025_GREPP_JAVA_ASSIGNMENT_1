package post;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Post {
    private String postTitle;
    private String postContent;

    public Post(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
}