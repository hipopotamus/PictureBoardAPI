package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Likes;
import pictureboard.api.domain.constant.OnClickStatus;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.exception.NotFoundSourceException;
import pictureboard.api.exception.SelfRelateException;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.LikesRepository;
import pictureboard.api.repository.PictureRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final AccountRepository accountRepository;
    private final PictureRepository pictureRepository;

    @Transactional
    public void makeLikes(Long accountId, Long pictureId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundSourceException("계정을 찾을 수 없습니다."));
        Picture picture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new NotFoundSourceException("사진을 찾을 수 없습니다."));
        Likes likes = likesRepository.findByMemberAndPicture(account, picture);

        if (accountId.equals(picture.getAccount().getId())) {
            throw new SelfRelateException("자기 자신의 사진에 '좋아요'할 수 없습니다.");
        }

        if (likes == null) {
            likesRepository.save(new Likes(account, picture));
            picture.addLikeCount();
        }
    }

    @Transactional
    public void deleteLikes(Long accountId, Long pictureId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundSourceException("계정을 찾을 수 없습니다."));
        Picture picture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new NotFoundSourceException("사진을 찾을 수 없습니다."));
        Likes likes = likesRepository.findByMemberAndPicture(account, picture);

        if (accountId.equals(picture.getAccount().getId())) {
            throw new SelfRelateException("자기 자신의 사진에 '좋아요'를 없앨 수 없습니다.");
        }

        if (likes == null) {
            throw new NotFoundSourceException("좋아요 관계를 찾을 수 없습니다.");
        }

        likes.softDelete();
        picture.removeLikeCount();
    }
}
