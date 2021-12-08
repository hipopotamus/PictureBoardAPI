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

    @ApiOperation(value = "likes 상태 변환", notes = "사진 아이디를 받습니다.\n" +
            "해당 API를 호출하는 것으로 로그인한 회원과 특정 사진과의 좋아요 상태를 활성화 또는 비활성화 할 수 있습니다.")
    @PostMapping("/likes/{pictureId}")
    public String clickLikes(@ApiIgnore @LoginAccount Long loginAccountId, @PathVariable Long pictureId) {
        likesService.onClick(loginAccountId, pictureId);
        return "clickLikes Success";
    }
}
