package post;

import lombok.RequiredArgsConstructor;
import spring.annotation.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post writePost(long boardId, String postTitle, String postContent) {
        Post post = new Post(boardId, postTitle, postContent);
        return postRepository.save(post);
    }

    public String readPost(Long postId) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new NoSuchPostException(postId);
        }
        String result = "";
        result += "[" + postId + "]번 게시글"
                + "\n작성일 : " + post.getPostTime()
                + "\n수정일 : " + post.getEditedTime()
                + "\n제목 : " + post.getPostTitle()
                + "\n내용 : " + post.getPostContent() + "\n";

        return result;
    }

    public Post deletePost(Long postId) {
        Post deletedPost = postRepository.delete(postId);
        if (deletedPost == null) {
            throw new NoSuchPostException(postId);
        }

        return deletedPost;
    }

    public boolean isPostExist(Long postId) {
        return postRepository.findById(postId) != null;
    }

    public void editPost(Long postId, String postTitle, String postContent) {
        Post post = new Post(null, postTitle, postContent);
        post.setPostId(postId);
        postRepository.update(post);
    }
}
