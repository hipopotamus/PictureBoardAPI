package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.service.LikesService;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"5. Likes"})
@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @ApiOperation(value = "좋아요 생성", notes = "사진의 아이디를 받아 로그인 회원과 해당 사진간 좋아요 관계를 생성합니다.")
    @PostMapping("/likes/{pictureId}")
    public String makeLikes(@ApiIgnore @LoginAccount Long accountId, @PathVariable Long pictureId) {
        likesService.makeLikes(accountId, pictureId);
        return "create likes success";
    }

    @ApiOperation(value = "좋아요 삭제", notes = "사진의 아이디를 받아 로그인 회원과 해당 사진간의 좋아요 관계를 삭제합니다.")
    @DeleteMapping("/likes/{pictureId}")
    public String deleteLikes(@ApiIgnore @LoginAccount Long accountId, @PathVariable Long pictureId) {
        likesService.deleteLikes(accountId, pictureId);
        return "delete likes success";
    }
}
