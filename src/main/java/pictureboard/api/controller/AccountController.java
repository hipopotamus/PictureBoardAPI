package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.domain.Account;
import pictureboard.api.dto.RoomPictureDto;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.form.SignUpForm;
import pictureboard.api.service.AccountService;
import pictureboard.api.validator.SignUpFormValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/loginAccount")
    public LoginAccountForm loginAccount(@LoginAccount LoginAccountForm loginAccountForm) {
        return loginAccountForm;
    }

    @PostMapping("/sign")
    public Object signUp(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) throws IOException {

        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }

        accountService.joinAccount(signUpForm.getUsername(), signUpForm.getPassword(), signUpForm.getNickname(),
                signUpForm.getProfileFile(), signUpForm.getGender(), signUpForm.getBirthDate());

        return "signUp success";
    }
}
