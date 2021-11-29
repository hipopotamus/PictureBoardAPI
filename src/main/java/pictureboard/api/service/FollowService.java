package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Follow;
import pictureboard.api.domain.OnClickStatus;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.FollowRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void onClick(Long activeFollowAccountId, Long passiveFollowAccountId) {
        Account activeFollowAccount = accountRepository.findById(activeFollowAccountId).orElse(null);
        Account passiveFollowAccount = accountRepository.findById(passiveFollowAccountId).orElse(null);
        Follow follow = followRepository.findByActiveFollowAccountAndPassiveFollowAccount(activeFollowAccount, passiveFollowAccount);

        if (follow == null) {
            followRepository.save(new Follow(activeFollowAccount, passiveFollowAccount, OnClickStatus.ON));
            activeFollowAccount.addActiveFollowCount();
            passiveFollowAccount.addPassiveFollowCount();
        } else {
            follow.switchStatus();
        }
    }
}
