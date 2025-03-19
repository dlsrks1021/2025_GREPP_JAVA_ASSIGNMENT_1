package post;

import board.BoardService;
import board.NoSuchBoardException;
import lombok.RequiredArgsConstructor;
import spring.CLI;
import spring.annotation.Controller;
import spring.annotation.GetMapping;
import spring.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final BoardService boardService;

    @GetMapping("/posts/add")
    public void add(@RequestParam("boardId") long boardId) {
        if (!boardService.isBoardExist(boardId)) {
            throw new NoSuchBoardException(boardId);
        }
        String postTitle = CLI.getUserInput("새로운 게시물을 작성합니다.\n제목을 입력하세요 > ");
        String postContent = CLI.getUserInput("내용을 입력하세요 > ");

        postService.writePost(boardId, postTitle, postContent);
    }

    @GetMapping("/posts/remove")
    public void remove(@RequestParam("postId") long postId) {
        Post deletedPost = postService.deletePost(postId);
        CLI.printPrompt(deletedPost.getPostTitle() + " 게시글이 삭제되었습니다.");
    }

    @GetMapping("/posts/edit")
    public void edit(@RequestParam("postId") long postId) {
        if (!postService.isPostExist(postId)) {
            throw new NoSuchPostException(postId);
        }

        String postTitle = CLI.getUserInput("게시글을 수정합니다.\n변경할 제목을 입력하세요 > ");
        String postContent = CLI.getUserInput("변경할 내용을 입력하세요 > ");
        postService.editPost(postId, postTitle, postContent);

        CLI.printPrompt("게시물이 수정되었습니다.");
    }

    @GetMapping("/posts/view")
    public void view(@RequestParam("postId") long postId) {
        CLI.printPrompt(postService.readPost(postId));
    }
}