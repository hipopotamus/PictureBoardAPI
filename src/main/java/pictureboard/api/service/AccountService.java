package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.form.SignUpForm;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Gender;
import pictureboard.api.domain.Img;
import pictureboard.api.repository.PictureRepository;
import pictureboard.api.repository.PictureRepositoryCustom;

import java.io.IOException;
import java.time.LocalDate;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final FileService fileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Value("${file.profile}")
    private String profileImgPath;

    @Transactional
    public Account joinAccount(String username, String password, String nickname, MultipartFile profileFile, Gender gender, LocalDate birthDate) throws IOException {
        String encodePassword = bCryptPasswordEncoder.encode(password);
        Img profileImg = fileService.storeFile(profileFile, profileImgPath);
        Account account = new Account(username, encodePassword, nickname, profileImg, gender, birthDate);
        account.settingRoles("ROLE_USER");
        return accountRepository.save(account);
    }

    public LoginAccountForm makeLoginAccountForm(String username) {
        Account account = accountRepository.findByUsername(username);
        LoginAccountForm loginAccountForm = modelMapper.map(account, LoginAccountForm.class);

        String profileFullPath = fileService.getPullPath(profileImgPath, account.getProfileImg().getStoreFileName());
        loginAccountForm.setProfileImgPath(profileFullPath);
        return loginAccountForm;
    }

    public boolean existByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean existByNickname(String nickname) {
        return accountRepository.existsByNickname(nickname);
    }
}
