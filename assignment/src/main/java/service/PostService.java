package service;

import lombok.RequiredArgsConstructor;
import model.Post;
import exception.NoSuchPostException;
import repository.PostRepository;
import framework.annotation.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post writePost(long boardId, String postTitle, String postContent, String author) {
        Post post = new Post(boardId, postTitle, postContent);
        post.setAuthor(author);
        return postRepository.save(post);
    }

    public Post getPost(long postId) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new NoSuchPostException(postId);
        }
        return post;
    }

    public String readPost(Long postId) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new NoSuchPostException(postId);
        }
        String result = "";
        result += "[" + postId + "]번 게시글"
                + "\n작성일 : " + post.getPostTime();
        result += post.getEditedTime() == null?
                    "" : "\n수정일 : " + post.getEditedTime();
        result += "\n제목 : " + post.getPostTitle()
                + "\n내용 : " + post.getPostContent()
                + "\n작성자 : " + post.getAuthor() + "\n";

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
