package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.service.AccountService;
import pictureboard.api.service.FollowService;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"4. Follow"})
@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    @ApiOperation(value = "팔로우 생성", notes = "팔로우할 대상의 아이디를 받아 로그인한 회원과 팔로우 관계를 생성합니다.")
    @PostMapping("/follow/{passiveFollowId}")
    public String createFollow(@ApiIgnore @LoginAccount Long accountId, @PathVariable("passiveFollowId") Long passiveFollowId) {
        followService.makeFollow(accountId, passiveFollowId);
        return "create follow success";
    }

    @ApiOperation(value = "팔로우 삭제", notes = "팔로우 관계를 끊을 대상의 아이디를 받아 로그인한 회원과 팔로우 관계를 삭제합니다.")
    @DeleteMapping("/follow/{passiveFollowId}")
    public String deleteFollow(@ApiIgnore @LoginAccount Long accountId, @PathVariable("passiveFollowId") Long passiveFollowId) {
        followService.deleteFollow(accountId, passiveFollowId);
        return "delete follow success";
    }
}
