package pictureboard.api.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pictureboard.api.domain.UserAccount;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.service.AccountService;

public class LoginAccountArgumentResolver implements HandlerMethodArgumentResolver {

    private AccountService accountService;

    public LoginAccountArgumentResolver(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasLoginAccountAnnotation = parameter.hasParameterAnnotation(LoginAccount.class);
        boolean hasAccountType = LoginAccountForm.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAccountAnnotation && hasAccountType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == "anonymousUser") {
            return null;
        }

        UserAccount userAccount = (UserAccount) principal;
        String username = userAccount.getAccount().getUsername();
        LoginAccountForm loginAccountForm = accountService.makeLoginAccountForm(username);
        return loginAccountForm;
    }
}
