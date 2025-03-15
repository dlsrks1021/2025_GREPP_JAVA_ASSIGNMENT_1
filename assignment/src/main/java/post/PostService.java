package post;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void writePost(String postTitle, String postContent) {
        Post post = new Post(postTitle, postContent);
        postRepository.save(post);
    }

    public String readAllPost() {
        List<Post> posts = postRepository.findAll();
        StringBuilder result = new StringBuilder();
        String prefix = "총 게시글은 " + posts.size() + "개 작성되어 있습니다.\n";
        result.append(prefix);
        for (Post post : posts) {
            result.append("\n");
            result.append(readPost(post.getPostId()));
        }

        return result.toString();
    }

    public String readPost(Long postId) {
        Post post = postRepository.find(postId);
        String result = "";
        result += postId + "번 게시물\n";
        result += "제목 : " + post.getPostTitle() + "\n";
        result += "내용 : " + post.getPostContent() + "\n";

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
