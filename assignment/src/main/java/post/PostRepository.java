package post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {

    private Long sequence = 0L;

    private final Map<Long, Post> posts = new HashMap<>();

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Post find(Long postId) {
        if (!posts.containsKey(postId)) {
            throw new NoSuchPostException(postId);
        }
        return posts.get(postId);
    }

    public void save(Post post) {
        post.setPostId(++sequence);
        posts.put(post.getPostId(), post);
    }

    public void delete(Long postId) {
        if (!posts.containsKey(postId)) {
            throw new NoSuchPostException(postId);
        }
        posts.remove(postId);
    }

    public void update(Post newPost) {
        Post oldPost = posts.get(newPost.getPostId());
        if (oldPost == null) {
            throw new NoSuchPostException(newPost.getPostId());
        }
        oldPost.setPostTitle(newPost.getPostTitle());
        oldPost.setPostContent(newPost.getPostContent());
    }
}
