package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.Picture;
import pictureboard.api.domain.PictureTag;
import pictureboard.api.domain.Tag;
import pictureboard.api.repository.PictureRepository;
import pictureboard.api.repository.PictureTagRepository;
import pictureboard.api.repository.TagRepository;

import java.util.ArrayList;
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
    public void creatPictureTagById(Long pictureId, Long tagId) {
        Picture picture = pictureRepository.findById(pictureId).orElse(null);
        Tag tag = tagRepository.findById(tagId).orElse(null);

        pictureTagRepository.save(new PictureTag(picture, tag));
    }

    @Transactional
    public void creatPictureTag(Picture picture, Tag tag) {
        pictureTagRepository.save(new PictureTag(picture, tag));
    }

    public void createPictureTags(Long pictureId, List<String> tagTitles) {
        if (tagTitles == null || tagTitles.isEmpty()) {
            return;
        }

        Picture picture = pictureRepository.findById(pictureId).orElse(null);
        for (String tagTitle : tagTitles) {
            Tag tag = tagRepository.existsByTitle(tagTitle) ? tagRepository.findByTitle(tagTitle) : tagService.createTage(tagTitle);
            creatPictureTag(picture, tag);
        }
    }

    public List<String> makeTagTitles(Long pictureId) {
        List<PictureTag> pictureTags = pictureTagRepository.findTitlesByPictureId(pictureId);

        if (pictureTags == null || pictureTags.isEmpty()) {
            return new ArrayList<String>();
        }

        List<String> result = new ArrayList<String>();
        pictureTags.forEach(pt -> result.add(pt.getTag().getTitle()));

        return result;
    }
}
