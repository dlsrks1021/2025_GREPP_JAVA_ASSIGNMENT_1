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

    public Post findById(Long postId) {
        return postMap.get(postId);
    }

    public Post save(Post post) {
        post.setPostId(++sequence);
        postMap.put(post.getPostId(), post);
        return post;
    }

    public Post delete(Long postId) {
        return postMap.remove(postId);
    }

    public void update(Post newPost) {
        Post post = postMap.get(newPost.getPostId());
        if (post == null) {
            throw new NoSuchPostException(newPost.getPostId());
        }
        post.setPostTitle(newPost.getPostTitle());
        post.setPostContent(newPost.getPostContent());
        post.setEditedTime(newPost.getPostTime());
    }
}
