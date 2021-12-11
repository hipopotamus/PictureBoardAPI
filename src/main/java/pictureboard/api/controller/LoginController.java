package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.exception.IllegalFormException;
import pictureboard.api.form.UsernamePasswordForm;
import pictureboard.api.service.AuthService;
import pictureboard.api.variable.JwtProperties;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = {"1. Login"})
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @ApiOperation(value = "로그인",
            notes = "아이디와 비밀번호를 받아 로그인을 시도합니다.\n" +
                    "로그인 성공시 Authorization 헤더에 인증 토큰이 발급됩니다.")
    @PostMapping("/myLogin")
    public String login(@Valid @RequestBody UsernamePasswordForm usernamePasswordForm, @ApiIgnore Errors errors,
                        HttpServletResponse response) {
        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }
        String jwtToken = authService.login(usernamePasswordForm);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        return "login success";
    }
}
