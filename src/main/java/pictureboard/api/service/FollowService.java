package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Follow;
import pictureboard.api.exception.SelfRelateException;
import pictureboard.api.exception.NotFoundSourceException;
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
            throw new SelfRelateException("자기 자신을 팔로우할 수 없습니다.");
        }
        Account activeFollowAccount = accountRepository.findById(activeFollowId)
                .orElseThrow(() -> new NotFoundSourceException("activeFollow 계정을 찾을 수 없습니다."));
        Account passiveFollowAccount = accountRepository.findById(passiveFollowId)
                .orElseThrow(() -> new NotFoundSourceException("passiveFollow 사진을 찾을 수 없습니다."));
        Follow follow = followRepository.findByActiveAndPassive(activeFollowId, passiveFollowId).orElse(null);

        if (follow == null) {
            followRepository.save(new Follow(activeFollowAccount, passiveFollowAccount));
            activeFollowAccount.addActiveFollowCount();
            passiveFollowAccount.addPassiveFollowCount();
        }
    }

    @Transactional
    public void deleteFollow(Long activeFollowId, Long passiveFollowId) {
        if (activeFollowId.equals(passiveFollowId)) {
            throw new SelfRelateException("자기 자신을 언팔로우할 수 없습니다.");
        }

        Account activeFollowAccount = accountRepository.findById(activeFollowId)
                .orElseThrow(() -> new NotFoundSourceException("activeFollow 계정을 찾을 수 없습니다."));
        Account passiveFollowAccount = accountRepository.findById(passiveFollowId)
                .orElseThrow(() -> new NotFoundSourceException("passiveFollow 계정을 찾을 수 없습니다."));
        Follow follow = followRepository.findByActiveAndPassive(activeFollowId, passiveFollowId)
                .orElseThrow(() -> new NotFoundSourceException("팔로우 관계를 찾을 수 없습니다."));

        follow.softDelete();
        activeFollowAccount.removeActiveFollowCount();
        passiveFollowAccount.removePassiveFollowCount();

    }
}
