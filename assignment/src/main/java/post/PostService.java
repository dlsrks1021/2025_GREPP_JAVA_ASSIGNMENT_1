package post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void writePost(String postTitle, String postContent) {
        Post post = new Post(postTitle, postContent);
        postRepository.save(post);
    }

    public String readPost() {
        Post post = postRepository.find();
        String result = "";
        result += "제목 : " + post.getPostTitle() + "\n";
        result += "내용 : " + post.getPostContent();

        return result;
    }

    public void deletePost() {
        postRepository.delete();
    }

    public void editPost(String postTitle, String postContent) {
        Post post = new Post(postTitle, postContent);
        postRepository.update(post);
    }
}
