package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Tag;
import pictureboard.api.dto.TagDto;
import pictureboard.api.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Tag createTage(String tagTitle) {
        return tagRepository.save(new Tag(tagTitle));
    }

    public List<TagDto> findAllAndOrderByRelatedPictureCount() {
        List<Tag> allAndOrderByRelatedPictureCount = tagRepository.findAllAndOrderByRelatedPictureCount();
        return allAndOrderByRelatedPictureCount.stream()
                .map(t -> modelMapper.map(t, TagDto.class))
                .collect(Collectors.toList());
    }

}
