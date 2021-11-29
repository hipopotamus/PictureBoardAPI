package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.form.LoginAccountForm;

@RestController
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/")
    public String home(@LoginAccount LoginAccountForm loginAccountForm) {
        return"home";
    }
}
