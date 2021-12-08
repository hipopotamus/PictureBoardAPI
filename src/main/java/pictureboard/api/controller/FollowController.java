package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.service.AccountService;
import pictureboard.api.service.FollowService;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"4. Follow"})
@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    @ApiOperation(value = "팔로우 상태 변환", notes = "회원 아이디를 받습니다.\n" +
            "해당 API를 호출하는 것으로 로그인한 회원과 특정 회원과의 팔로우 상태를 활성화 또는 비활성화 할 수 있습니다.")
    @PostMapping("/follow/{passiveAccountId}")
    public AccountDto clickFollow(@ApiIgnore @LoginAccount Long accountId, @PathVariable Long passiveAccountId) {
        followService.onClick(accountId, passiveAccountId);
        return accountService.makeAccountDtoById(accountId);
    }
}
