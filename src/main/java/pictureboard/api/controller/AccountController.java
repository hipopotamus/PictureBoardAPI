package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.dto.Result;
import pictureboard.api.form.SignUpForm;
import pictureboard.api.service.AccountService;
import pictureboard.api.validator.SignUpFormValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @PostMapping("/sign")
    public Object signUp(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) throws IOException {

        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }

        Account account = accountService.joinAccount(signUpForm.getUsername(), signUpForm.getPassword(), signUpForm.getNickname(),
                signUpForm.getProfileFile(), signUpForm.getGender(), signUpForm.getBirthDate());

        return accountService.makeAccountDtoByAccount(account);
    }

    @GetMapping("/loginAccount")
    public AccountDto loginAccount(@LoginAccount Long loginAccountId) {
        return accountService.makeAccountDtoById(loginAccountId);
    }

    @GetMapping("/account/{accountId}")
    public AccountDto account(@PathVariable("accountId") Long accountId) {
        return accountService.makeAccountDtoById(accountId);
    }

    @GetMapping("/search/accounts")
    public Page<AccountDto> searchAccounts(@Param("nickname") String nickname, Pageable pageable) {
        return accountService.searchPageByNickname(nickname, pageable);
    }

    @GetMapping("account/passiveFollowAccount/{accountId}")
    public Result<List<AccountDto>> findPassiveFollowAccount(@PathVariable("accountId") Long accountId) {
        List<AccountDto> passiveFollowAccounts = accountService.findPassiveFollowAccounts(accountId);
        Result<List<AccountDto>> result = new Result<>();
        result.setData(passiveFollowAccounts);
        return result;
    }

    @GetMapping("account/activeFollowAccount/{accountId}")
    public Result<List<AccountDto>> findActiveFollowAccount(@PathVariable("accountId") Long accountId) {
        List<AccountDto> passiveFollowAccounts = accountService.findActiveFollowAccounts(accountId);
        Result<List<AccountDto>> result = new Result<>();
        result.setData(passiveFollowAccounts);
        return result;
    }

    @PostMapping("/account/nickname")
    public AccountDto updateNickname(@LoginAccount Long loginAccountId, @RequestParam String nickname) {
        return accountService.updateNickname(loginAccountId, nickname);
    }

    @PostMapping("/account/profileImg")
    public AccountDto updateProfileImg(@LoginAccount Long loginAccountId, @RequestParam MultipartFile profileFile) throws IOException {
        return accountService.updateProfileImg(loginAccountId, profileFile);
    }


}
