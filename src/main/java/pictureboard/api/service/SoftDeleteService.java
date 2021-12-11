package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.BaseTime;
import pictureboard.api.domain.entity.*;
import pictureboard.api.exception.NotFoundSourceException;
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
        if (c.isAssignableFrom(PictureTag.class)) {
            softDeletePictureTag(id);
            return;
        }

        throw new RuntimeException();
    }

    private void softDeleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("계정을 찾을 수 없습니다."));

        List<Follow> follows = followRepository.findByAccountId(account.getId());
        List<Likes> likesList = likesRepository.findByAccountId(account.getId());
        List<Picture> pictures = pictureRepository.findByAccountId(account.getId());

        follows.forEach(BaseTime::softDelete);
        likesList.forEach(BaseTime::softDelete);
        pictures.forEach(p -> softDeletePicture(p.getId()));
        account.softDelete();

    }

    private void softDeletePicture(Long id) {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("사진을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByPictureId(picture.getId());
        List<Likes> likesList = likesRepository.findByPictureId(picture.getId());
        List<PictureTag> pictureTags = pictureTagRepository.findByPictureId(picture.getId());

        comments.forEach(BaseTime::softDelete);
        likesList.forEach(BaseTime::softDelete);
        pictureTags.forEach(BaseTime::softDelete);
        picture.softDelete();
    }

    private void softDeleteFollow(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("팔로우 관계를 찾을 수 없습니다."));
        follow.softDelete();
    }

    private void softDeleteLikes(Long id) {
        Likes likes = likesRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("좋아요 관계를 찾을 수 없습니다."));
        likes.softDelete();
    }

    private void softDeleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("댓글을 찾을 수 없습니다."));
        comment.softDelete();
    }

    private void softDeletePictureTag(Long id) {
        PictureTag pictureTag = pictureTagRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("사진의 태그를 찾을 수 없습니다."));
        pictureTag.softDelete();
    }

}
