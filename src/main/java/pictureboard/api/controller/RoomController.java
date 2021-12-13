package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.dto.ChatRoomDto;
import pictureboard.api.service.AccountService;
import pictureboard.api.service.ChatRoomService;

import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomService chatRoomService;
    private final AccountService accountService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public String rooms(Model model){

        log.info("# All Chat Rooms");
        model.addAttribute("list", chatRoomService.findAll());

        return "/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room/{roomId}")
    public String getRoom(@PathVariable("roomId") Long roomId, Model model){

        Random random = new Random();
        log.info("# get Chat Room, roomID : " + roomId);
        AccountDto account = accountService.makeAccountDtoById((long) (random.nextInt(25) + 1));

        ChatRoomDto chatRoomDto = chatRoomService.findById(roomId);
        model.addAttribute("room", chatRoomDto);
        model.addAttribute("account", account);
        return "/chat/room";
    }
}
