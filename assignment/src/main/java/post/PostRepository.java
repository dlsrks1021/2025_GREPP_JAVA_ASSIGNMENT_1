package post;

import spring.annotation.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    private Long sequence = 0L;

    private final Map<Long, Post> postMap = new HashMap<>();

    public List<Post> findAll() {
        return new ArrayList<>(postMap.values());
    }

    public Post find(Long postId) {
        if (!postMap.containsKey(postId)) {
            throw new NoSuchPostException(postId);
        }
        return postMap.get(postId);
    }

    public void save(Post post) {
        post.setPostId(++sequence);
        postMap.put(post.getPostId(), post);
    }

    public void delete(Long postId) {
        if (!postMap.containsKey(postId)) {
            throw new NoSuchPostException(postId);
        }
        postMap.remove(postId);
    }

    public void update(Post newPost) {
        Post oldPost = postMap.get(newPost.getPostId());
        if (oldPost == null) {
            throw new NoSuchPostException(newPost.getPostId());
        }
        oldPost.setPostTitle(newPost.getPostTitle());
        oldPost.setPostContent(newPost.getPostContent());
    }
}
