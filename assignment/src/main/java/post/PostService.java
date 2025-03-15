package post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void writePost(String postTitle, String postContent) {
        Post post = new Post(postTitle, postContent);
        postRepository.save(post);
    }

    public String readPost(Long postId) {
        Post post = postRepository.find(postId);
        String result = "";
        result += postId + "번 게시물\n";
        result += "제목 : " + post.getPostTitle() + "\n";
        result += "내용 : " + post.getPostContent();

        return result;
    }

    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }

    public void editPost(Long postId, String postTitle, String postContent) {
        Post post = new Post(postTitle, postContent);
        post.setPostId(postId);
        postRepository.update(post);
    }
}
