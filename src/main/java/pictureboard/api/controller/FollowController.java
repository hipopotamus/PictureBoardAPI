package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.service.FollowService;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{memberId}")
    public String clickFollow(@LoginAccount LoginAccountForm loginAccountForm, @PathVariable Long memberId) {
        followService.onClick(loginAccountForm.getId(), memberId);
        return "clickFollow Success";
    }
}
