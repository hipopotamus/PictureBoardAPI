package pictureboard.api.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.CommentDto;
import pictureboard.api.dto.Result;
import pictureboard.api.form.CommentForm;
import pictureboard.api.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public String createComment(@LoginAccount Long loginAccountId, CommentForm commentForm) {
        commentService.createComment(commentForm.getPictureId(), loginAccountId, commentForm.getContent());
        return "create comment success";
    }

    @GetMapping("/comment/{pictureId}")
    public Result<List<CommentDto>> findByPicture(@PathVariable("pictureId") Long pictureId) {
        List<CommentDto> commentDtoList = commentService.findComment(pictureId);
        return new Result<>(commentDtoList);
    }
}
