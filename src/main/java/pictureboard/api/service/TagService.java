package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.Tag;
import pictureboard.api.repository.TagRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag createTage(String tagTitle) {
        return tagRepository.save(new Tag(tagTitle));
    }
}
