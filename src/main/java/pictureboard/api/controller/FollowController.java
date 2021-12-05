package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.service.AccountService;
import pictureboard.api.service.FollowService;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    @PostMapping("/follow/{passiveAccountId}")
    public AccountDto clickFollow(@LoginAccount Long accountId, @PathVariable Long passiveAccountId) {
        followService.onClick(accountId, passiveAccountId);
        return accountService.makeAccountDtoById(accountId);
    }
}
