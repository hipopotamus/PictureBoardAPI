package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.dto.Result;
import pictureboard.api.exception.IllegalFormException;
import pictureboard.api.form.AccountForm;
import pictureboard.api.form.AccountNicknameForm;
import pictureboard.api.form.AccountProfileForm;
import pictureboard.api.service.AccountService;
import pictureboard.api.validator.AccountFormValidator;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"2. Account"})
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountFormValidator accountFormValidator;

    @InitBinder("accountForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountFormValidator);
    }

    @ApiOperation(value = "회원 가입", notes = "회원 정보를 받아 새로운 회원을 생성합니다.")
    @PostMapping("/account")
    public Object signUp(@Valid @ModelAttribute AccountForm accountForm, @ApiIgnore Errors errors) throws IOException {

        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }

        Account account = accountService.joinAccount(accountForm.getUsername(), accountForm.getPassword(), accountForm.getNickname(),
                accountForm.getProfileFile(), accountForm.getGender(), accountForm.getBirthDate());

        return accountService.makeAccountDtoByAccount(account);
    }

    @ApiOperation(value = "내 닉네임 수정", notes = "변경할 닉네임을 받아 회원의 닉네임을 수정합니다.")
    @PostMapping("/account/nickname")
    public AccountDto updateNickname(@ApiIgnore @LoginAccount Long loginAccountId,
                                     @Valid @RequestBody AccountNicknameForm accountNicknameForm,
                                     @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }
        return accountService.updateNickname(loginAccountId, accountNicknameForm.getNickname());
    }

    @ApiOperation(value = "내 프로필 이미지 수정", notes = "변경할 프로필 이미지를 받아 회원의 프로필 이미지를 수정합니다.")
    @PostMapping("/account/profileImg")
    public AccountDto updateProfileImg(@ApiIgnore @LoginAccount Long loginAccountId,
                                       @Valid @RequestBody AccountProfileForm accountProfileForm,
                                       @ApiIgnore Errors errors) throws IOException {
        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }
        return accountService.updateProfileImg(loginAccountId, accountProfileForm.getProfileFile());
    }

    @ApiOperation(value = "로그인 회원 정보 조회", notes = "현재 로그인한 회원의 정보를 조회합니다.")
    @GetMapping("/loginAccount")
    public AccountDto loginAccount(@ApiIgnore @LoginAccount Long loginAccountId) {
        return accountService.makeAccountDtoById(loginAccountId);
    }

    @ApiOperation(value = "회원 정보 조회", notes = "회원 아이디로 회원 정보를 조회합니다.")
    @GetMapping("/account/{accountId}")
    public AccountDto account(@PathVariable("accountId") Long accountId) {
        return accountService.makeAccountDtoById(accountId);
    }

    @ApiOperation(value = "회원 검색", notes = "검색 조건으로 닉네임을 받아 조건에 맞는 회원을 모두 조회합니다.\n" +
            "'페이지 번호'와 '페이지 크기'를 받아 페이지로 조회됩니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", dataType = "int", paramType = "query")})
    @GetMapping("/account/page/search")
    public Page<AccountDto> searchAccounts(@RequestParam("nickname") String nickname, @ApiIgnore Pageable pageable) {
        return accountService.searchPageByNickname(nickname, pageable);
    }

    @ApiOperation(value = "내가 팔로우한 회원 조회", notes = "회원 아이디를 받아 해당 회원이 팔로우한 모든 회원을 조회합니다.")
    @GetMapping("account/activeFollowAccount/{accountId}")
    public Result<List<AccountDto>> findActiveFollowAccount(@PathVariable("accountId") Long accountId) {
        List<AccountDto> passiveFollowAccounts = accountService.findActiveFollowAccounts(accountId);
        Result<List<AccountDto>> result = new Result<>();
        result.setData(passiveFollowAccounts);
        return result;
    }

    @ApiOperation(value = "나를 팔로우한 회원 조회", notes = "회원 아이디를 받아 해당 회원을 팔로우한 모든 회원을 조회합니다.")
    @GetMapping("account/passiveFollowAccount/{accountId}")
    public Result<List<AccountDto>> findPassiveFollowAccount(@PathVariable("accountId") Long accountId) {
        List<AccountDto> passiveFollowAccounts = accountService.findPassiveFollowAccounts(accountId);
        Result<List<AccountDto>> result = new Result<>();
        result.setData(passiveFollowAccounts);
        return result;
    }

    @DeleteMapping("account/{accountId}")
    public void deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @DeleteMapping("account/delete/{accountId}")
    public void deletehardAccount(@PathVariable Long accountId) {
        accountService.hardDeleteAccount(accountId);
    }
}
