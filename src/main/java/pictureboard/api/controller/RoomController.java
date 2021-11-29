package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.RoomDto;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.service.RoomService;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room/{accountId}")
    public RoomDto room(@LoginAccount LoginAccountForm loginAccountForm, @PathVariable("accountId") Long accountId) {
        return roomService.makeRoomDto(loginAccountForm.getId(), accountId);
    }
}
