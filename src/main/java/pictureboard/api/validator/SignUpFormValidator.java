package pictureboard.api.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pictureboard.api.form.SignUpForm;
import pictureboard.api.service.AccountService;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;
        if (accountService.existByUsername(signUpForm.getUsername())) {
            errors.reject("usernameDuplication", "이미 사용중인 아이디입니다.");
        }

        if (accountService.existByNickname(signUpForm.getNickname())) {
            errors.reject("nicknameDuplicate", "이미 사용중인 닉네임 입니다.");
        }
    }
}
