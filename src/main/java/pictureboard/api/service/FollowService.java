package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Follow;
import pictureboard.api.domain.constant.OnClickStatus;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.FollowRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void makeFollow(Long activeFollowId, Long passiveFollowId) {
        if (activeFollowId.equals(passiveFollowId)) {
            throw new RuntimeException("don't follow myself");
        }
        Account activeFollowAccount = accountRepository.findById(activeFollowId).orElseThrow(RuntimeException::new);
        Account passiveFollowAccount = accountRepository.findById(passiveFollowId).orElseThrow(RuntimeException::new);
        Follow follow = followRepository.findByActiveFollowAccountAndPassiveFollowAccount(activeFollowAccount, passiveFollowAccount);

        if (follow == null) {
            followRepository.save(new Follow(activeFollowAccount, passiveFollowAccount));
            activeFollowAccount.addActiveFollowCount();
            passiveFollowAccount.addPassiveFollowCount();
        }
    }

    @Transactional
    public void deleteFollow(Long activeFollowId, Long passiveFollowId) {
        if (activeFollowId.equals(passiveFollowId)) {
            throw new RuntimeException("don't unfollow myself");
        }

        Account activeFollowAccount = accountRepository.findById(activeFollowId).orElseThrow(RuntimeException::new);
        Account passiveFollowAccount = accountRepository.findById(passiveFollowId).orElseThrow(RuntimeException::new);
        Follow follow = followRepository.findByActiveFollowAccountAndPassiveFollowAccount(activeFollowAccount, passiveFollowAccount);

        if (follow == null) {
            throw new RuntimeException("don't unfollow them");
        }

        follow.softDelete();
        activeFollowAccount.removeActiveFollowCount();
        passiveFollowAccount.removePassiveFollowCount();

    }
}
