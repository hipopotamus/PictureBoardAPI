package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Likes;
import pictureboard.api.domain.OnClickStatus;
import pictureboard.api.domain.Picture;
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
    public void onClick(Long loginAccountId, Long pictureId) {
        Account loginAccount = accountRepository.findById(loginAccountId).orElse(null);
        Picture picture = pictureRepository.findById(pictureId).orElse(null);
        Likes likes = likesRepository.findByMemberAndPicture(loginAccount, picture);

        if (likes == null) {
            likesRepository.save(new Likes(loginAccount, picture, OnClickStatus.ON));
            picture.addLikeCount();
        } else {
            likes.switchStatus();
        }
    }
}
