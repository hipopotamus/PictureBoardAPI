package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.domain.entity.PictureTag;
import pictureboard.api.domain.entity.Tag;
import pictureboard.api.exception.AuthException;
import pictureboard.api.exception.NotFoundSourceException;
import pictureboard.api.repository.PictureRepository;
import pictureboard.api.repository.PictureTagRepository;
import pictureboard.api.repository.TagRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PictureTagService {

    private final PictureRepository pictureRepository;
    private final TagRepository tagRepository;
    private final PictureTagRepository pictureTagRepository;
    private final TagService tagService;

    @Transactional
    public void createPictureTag(Picture picture, Tag tag) {
        pictureTagRepository.save(new PictureTag(picture, tag));
        tag.addRelatedPictureCount();
    }

    @Transactional
    public void createPictureTags(Long loginAccountId, Long pictureId, List<String> tagTitles) {
        if (tagTitles == null || tagTitles.isEmpty()) {
            return;
        }

        Picture picture = pictureRepository.findWithAccount(pictureId)
                .orElseThrow(() -> new NotFoundSourceException("사진을 찾을 수 없습니다."));

        if (!picture.getAccount().getId().equals(loginAccountId)) {
            throw new AuthException("로그인 계정의 사진이 아닙니다.");
        }

        for (String tagTitle : tagTitles) {
            Tag tag = tagRepository.existsByTitle(tagTitle)
                    ? tagRepository.findByTitle(tagTitle).orElse(null)
                    : tagService.createTage(tagTitle);
            createPictureTag(picture, tag);
        }
    }

    @Transactional
    public void deletePictureTags(Long loginAccountId, Long pictureId, List<String> tagTitles) {

        Picture picture = pictureRepository.findWithAccount(pictureId)
                .orElseThrow(() -> new NotFoundSourceException("사진을 찾을 수 없습니다."));

        if (!picture.getAccount().getId().equals(loginAccountId)) {
            throw new AuthException("로그인 계정의 사진이 아닙니다.");
        }

        for (String tagTitle : tagTitles) {
            Tag tag = tagRepository.findByTitle(tagTitle)
                    .orElseThrow(() -> new NotFoundSourceException("태그를 찾을 수 없습니다."));

            PictureTag pictureTag = pictureTagRepository.findByPictureAndTag(picture, tag);
            pictureTag.softDelete();
            tag.removeRelatedPictureCount();
        }
    }
}
