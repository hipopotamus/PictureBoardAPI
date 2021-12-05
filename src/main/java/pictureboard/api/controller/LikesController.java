package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.service.LikesService;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/likes/{pictureId}")
    public String clickLikes(@LoginAccount Long loginAccountId, @PathVariable Long pictureId) {
        likesService.onClick(loginAccountId, pictureId);
        return "clickLikes Success";
    }
}
