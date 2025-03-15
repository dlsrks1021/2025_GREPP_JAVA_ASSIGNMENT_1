package post;

import java.util.Stack;

public class PostRepository {

    private Long sequence = 0L;

    private final Stack<Post> posts = new Stack<>();

    public Post find() {
        return posts.peek();
    }

    public void save(Post post) {
        post.setPostId(++sequence);
        posts.push(post);
    }

    public void delete() {
        posts.pop();
    }

    public void update(Post post) {
        posts.pop();
        posts.push(post);
    }
}
