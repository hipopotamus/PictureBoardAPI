package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Likes;
import pictureboard.api.domain.constant.OnClickStatus;
import pictureboard.api.domain.entity.Picture;
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
        Account account = accountRepository.findById(accountId).orElseThrow(RuntimeException::new);
        Picture picture = pictureRepository.findById(pictureId).orElseThrow(RuntimeException::new);
        Likes likes = likesRepository.findByMemberAndPicture(account, picture);

        if (accountId.equals(picture.getAccount().getId())) {
            throw new RuntimeException("don't likes myself");
        }

        if (likes == null) {
            likesRepository.save(new Likes(account, picture));
            picture.addLikeCount();
        }
    }

    @Transactional
    public void deleteLikes(Long accountId, Long pictureId) {
        Account account = accountRepository.findById(accountId).orElseThrow(RuntimeException::new);
        Picture picture = pictureRepository.findById(pictureId).orElseThrow(RuntimeException::new);
        Likes likes = likesRepository.findByMemberAndPicture(account, picture);

        if (accountId.equals(picture.getAccount().getId())) {
            throw new RuntimeException("don't unlikes myself");
        }

        if (likes == null) {
            throw new RuntimeException("there isn't likes");
        }

        likes.softDelete();
        picture.removeLikeCount();
    }
}
