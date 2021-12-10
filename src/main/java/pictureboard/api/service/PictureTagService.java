package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.domain.entity.PictureTag;
import pictureboard.api.domain.entity.Tag;
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
    private final SoftDeleteService softDeleteService;

    @Transactional
    public void createPictureTag(Picture picture, Tag tag) {
        PictureTag pictureTag = pictureTagRepository.save(new PictureTag(picture, tag));
        tag.addRelatedPictureCount();
    }

    @Transactional
    public void createPictureTags(Long pictureId, List<String> tagTitles) {
        if (tagTitles == null || tagTitles.isEmpty()) {
            return;
        }

        Picture picture = pictureRepository.findById(pictureId).orElse(null);
        for (String tagTitle : tagTitles) {
            Tag tag = tagRepository.existsByTitle(tagTitle) ? tagRepository.findByTitle(tagTitle) : tagService.createTage(tagTitle);
            createPictureTag(picture, tag);
        }
    }

    @Transactional
    public void deletePictureTags(Long pictureId, List<String> tagTitles) {
        Picture picture = pictureRepository.findById(pictureId).orElseThrow(RuntimeException::new);

        for (String tagTitle : tagTitles) {
            Tag tag = tagRepository.findByTitle(tagTitle);
            PictureTag pictureTag = pictureTagRepository.findByPictureAndTag(picture, tag);
            pictureTag.softDelete();
            tag.removeRelatedPictureCount();
        }
    }
}
