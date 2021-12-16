package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pictureboard.api.domain.Img;
import pictureboard.api.domain.UserAccount;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.repository.AccountRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthAttributeService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;

    public UserAccount createOauthPrincipal(OAuth2User oAuth2User, String name) {
        if (name.equals("google")) {
            return ofGoogle(oAuth2User, name);
        }
        if (name.equals("naver")) {
            return ofNaver(oAuth2User, name);
        }
        throw new RuntimeException();
    }

    private UserAccount ofNaver(OAuth2User oAuth2User, String name) {
        Map<String, String> response = oAuth2User.getAttribute("response");
        String username = name + "_" + response.get("email");
        Account findAccount = accountRepository.findByUsername(username);

        if (findAccount == null) {
            String password = bCryptPasswordEncoder.encode("OauthAccount");
            String picture = response.get("profile_image");
            Account account = new Account(username, password, new Img(picture, picture, picture));
            account.settingRoles("ROLE_USER");
            accountRepository.save(account);
            return new UserAccount(account, oAuth2User.getAttributes());
        }
        return new UserAccount(findAccount, oAuth2User.getAttributes());
    }

    private UserAccount ofGoogle(OAuth2User oAuth2User, String name) {
        String username = name + "_" + oAuth2User.getAttribute("email");
        Account findAccount = accountRepository.findByUsername(username);

        if (findAccount == null) {
            String password = bCryptPasswordEncoder.encode("OauthAccount");
            String picture = oAuth2User.getAttribute("picture");
            Account account = new Account(username, password, new Img(picture, picture, picture));
            account.settingRoles("ROLE_USER");
            accountRepository.save(account);
            return new UserAccount(account, oAuth2User.getAttributes());
        }
        return new UserAccount(findAccount, oAuth2User.getAttributes());
    }
}
