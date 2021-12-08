package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.CommentDto;
import pictureboard.api.dto.Result;
import pictureboard.api.form.CommentForm;
import pictureboard.api.service.CommentService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = {"6. Comment"})
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "comment 생성", notes = "사진 아이디와 comment의 내용을 받아, 현재 로그인 한 회원으로 comment를 생성합니다.")
    @PostMapping("/comment")
    public String createComment(@ApiIgnore @LoginAccount Long loginAccountId, @RequestBody CommentForm commentForm) {
        commentService.createComment(commentForm.getPictureId(), loginAccountId, commentForm.getContent());
        return "create comment success";
    }

    @ApiOperation(value = "사진 comment 조회", notes = "사진의 아이디를 받아 해당 사진의 comment를 모두 조회합니다.")
    @GetMapping("/comment/{pictureId}")
    public Result<List<CommentDto>> findByPicture(@PathVariable("pictureId") Long pictureId) {
        List<CommentDto> commentDtoList = commentService.findComment(pictureId);
        return new Result<>(commentDtoList);
    }
}
