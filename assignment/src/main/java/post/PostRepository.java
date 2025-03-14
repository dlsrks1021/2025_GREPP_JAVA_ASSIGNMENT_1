package post;

import java.util.Stack;

public class PostRepository {

    private final Stack<Post> posts = new Stack<>();

    public Post find() {
        return posts.peek();
    }

    public void save(Post post) {
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
