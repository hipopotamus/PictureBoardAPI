package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.BaseTime;
import pictureboard.api.domain.entity.*;
import pictureboard.api.repository.*;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SoftDeleteService {

    public final AccountRepository accountRepository;
    public final PictureRepository pictureRepository;
    public final FollowRepository followRepository;
    public final LikesRepository likesRepository;
    public final CommentRepository commentRepository;
    public final PictureTagRepository pictureTagRepository;

    public void softDelete(Long id, Class c) {
        if (c.isAssignableFrom(Account.class)) {
            softDeleteAccount(id);
            return;
        }
        if (c.isAssignableFrom(Picture.class)) {
            softDeletePicture(id);
            return;
        }
        if (c.isAssignableFrom(Follow.class)) {
            softDeleteFollow(id);
            return;
        }
        if (c.isAssignableFrom(Likes.class)) {
            softDeleteLikes(id);
            return;
        }
        if (c.isAssignableFrom(Comment.class)) {
            softDeleteComment(id);
            return;
        }

        throw new RuntimeException();
    }

    private void softDeleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(RuntimeException::new);

        List<Follow> follows = followRepository.findByAccountId(account.getId());
        List<Likes> likesList = likesRepository.findByAccountId(account.getId());
        List<Picture> pictures = pictureRepository.findByAccountId(account.getId());

        follows.forEach(BaseTime::softDelete);
        likesList.forEach(BaseTime::softDelete);
        pictures.forEach(p -> softDeletePicture(p.getId()));
        account.softDelete();

    }

    private void softDeletePicture(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(RuntimeException::new);

        List<Comment> comments = commentRepository.findByPictureId(picture.getId());
        List<Likes> likesList = likesRepository.findByPictureId(picture.getId());
        List<PictureTag> pictureTags = pictureTagRepository.findByPictureId(picture.getId());

        comments.forEach(BaseTime::softDelete);
        likesList.forEach(BaseTime::softDelete);
        pictureTags.forEach(BaseTime::softDelete);
        picture.softDelete();
    }

    private void softDeleteFollow(Long id) {
        Follow follow = followRepository.findById(id).orElseThrow(RuntimeException::new);
        follow.softDelete();
    }

    private void softDeleteLikes(Long id) {
        Likes likes = likesRepository.findById(id).orElseThrow(RuntimeException::new);
        likes.softDelete();
    }

    private void softDeleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(RuntimeException::new);
        comment.softDelete();
    }

}
