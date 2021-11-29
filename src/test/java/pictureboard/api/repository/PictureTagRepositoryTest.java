package pictureboard.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pictureboard.api.domain.PictureTag;
import pictureboard.api.service.PictureTagService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PictureTagRepositoryTest {

    @Autowired
    PictureTagRepository pictureTagRepository;

    @Autowired
    PictureTagService pictureTagService;

    @Test
    public void findTitlesTest() {
        List<String> strings = pictureTagService.makeTagTitles(9L);
        System.out.println("==========================");
        for (String string : strings) {
            System.out.println(string);
        }
    }
}