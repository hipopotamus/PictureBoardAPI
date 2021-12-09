package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.constant.Gender;
import pictureboard.api.domain.Img;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final FileService fileService;
    private final SoftDeleteService softDeleteService;
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

    @Transactional
    public Account joinAccountTest(String username, String password, String nickname, Gender gender, LocalDate birthDate) throws IOException {
        String encodePassword = bCryptPasswordEncoder.encode(password);
        Img profileImg = new Img("testFile", "testStoreFile", "testFullPath");
        Account account = new Account(username, encodePassword, nickname, profileImg, gender, birthDate);
        account.settingRoles("ROLE_USER");
        return accountRepository.save(account);
    }

    public AccountDto makeAccountDtoById(Long loginAccountId) {
        Account account = accountRepository.findById(loginAccountId).orElse(null);
        return makeAccountDtoByAccount(account);
    }

    public AccountDto makeAccountDtoByAccount(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    public Page<AccountDto> searchPageByNickname(String nickname, Pageable pageable) {
        return accountRepository.searchPageByNickname(nickname, pageable);
    }

    @Transactional
    public AccountDto updateNickname(Long accountId, String nickname) {
        Account account = accountRepository.findById(accountId).orElse(null);
        account.updateNickname(nickname);
        return makeAccountDtoByAccount(account);
    }

    @Transactional
    public AccountDto updateProfileImg(Long accountId, MultipartFile profileFile) throws IOException {
        Account account = accountRepository.findById(accountId).orElse(null);
        Img profileImg = fileService.storeFile(profileFile, profileImgPath);
        account.updateProfileImg(profileImg);

        return makeAccountDtoByAccount(account);
    }

    public List<AccountDto> findPassiveFollowAccounts(Long accountId) {
        List<Account> passiveFollowAccounts = accountRepository.findPassiveFollowAccount(accountId);
        return passiveFollowAccounts.stream()
                .map(this::makeAccountDtoByAccount)
                .collect(Collectors.toList());
    }

    public List<AccountDto> findActiveFollowAccounts(Long accountId) {
        List<Account> activeFollowAccounts = accountRepository.findActiveFollowAccount(accountId);
        return activeFollowAccounts.stream()
                .map(this::makeAccountDtoByAccount)
                .collect(Collectors.toList());
    }

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(NullPointerException::new);
    }

    public boolean existByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean existByNickname(String nickname) {
        return accountRepository.existsByNickname(nickname);
    }

    public Account findByNickname(String nickname) {
        return accountRepository.findByNickname(nickname);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        softDeleteService.softDelete(accountId, Account.class);
    }
}
