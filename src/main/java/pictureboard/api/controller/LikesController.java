package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.domain.Likes;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.service.LikesService;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/likes/{pictureId}")
    public String clickLikes(@LoginAccount LoginAccountForm loginAccountForm, @PathVariable Long pictureId) {
        likesService.onClick(loginAccountForm.getId(), pictureId);
        return "clickLikes Success";
    }
}
