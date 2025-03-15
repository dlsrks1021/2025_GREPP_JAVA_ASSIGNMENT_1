package post;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PostRepository {

    private Long sequence = 0L;

    private final Map<Long, Post> posts = new HashMap<>();

    public Post find(Long postId) {
        return posts.get(postId);
    }

    public void save(Post post) {
        post.setPostId(++sequence);
        posts.put(post.getPostId(), post);
    }

    public void delete(Long postId) {
        posts.remove(postId);
    }

    public void update(Post newPost) {
        Post oldPost = posts.get(newPost.getPostId());
        oldPost.setPostTitle(newPost.getPostTitle());
        oldPost.setPostContent(newPost.getPostContent());
    }
}
