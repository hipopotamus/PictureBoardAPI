package pictureboard.api.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pictureboard.api.form.AccountForm;
import pictureboard.api.service.AccountService;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountForm accountForm = (AccountForm) target;
        if (accountService.existByUsername(accountForm.getUsername())) {
            errors.reject("usernameDuplication", "이미 사용중인 아이디입니다.");
        }

        if (accountService.existByNickname(accountForm.getNickname())) {
            errors.reject("nicknameDuplicate", "이미 사용중인 닉네임 입니다.");
        }
    }
}
