package controller;

import framework.Session;
import service.BoardService;
import exception.NoSuchBoardException;
import lombok.RequiredArgsConstructor;
import model.Post;
import exception.NoSuchPostException;
import service.PostService;
import framework.annotation.Controller;
import framework.annotation.GetMapping;
import framework.annotation.RequestParam;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final BoardService boardService;
    private final Scanner scanner;

    @GetMapping("/posts/add")
    public void add(@RequestParam("boardId") long boardId, Session session) {
        if (!boardService.isBoardExist(boardId)) {
            throw new NoSuchBoardException(boardId);
        }
        System.out.println("새로운 게시물을 작성합니다.");
        System.out.print("제목을 입력하세요 > ");
        String postTitle = scanner.nextLine();
        System.out.print("내용을 입력하세요 > ");
        String postContent = scanner.nextLine();
        String userName = (String) session.getAttribute("userName");
        String author = userName != null ? userName : "비회원";

        postService.writePost(boardId, postTitle, postContent, author);
    }

    @GetMapping("/posts/remove")
    public void remove(@RequestParam("postId") long postId) {
        Post deletedPost = postService.deletePost(postId);
        System.out.println(deletedPost.getPostTitle() + " 게시글이 삭제되었습니다.");
    }

    @GetMapping("/posts/edit")
    public void edit(@RequestParam("postId") long postId) {
        if (!postService.isPostExist(postId)) {
            throw new NoSuchPostException(postId);
        }

        System.out.println("게시글을 수정합니다.");
        System.out.print("변경할 제목을 입력하세요 > ");
        String postTitle = scanner.nextLine();
        System.out.print("변경할 내용을 입력하세요 > ");
        String postContent = scanner.nextLine();

        postService.editPost(postId, postTitle, postContent);

        System.out.println("게시물이 수정되었습니다.");
    }

    @GetMapping("/posts/view")
    public void view(@RequestParam("postId") long postId) {
        System.out.println(postService.readPost(postId));
    }
}