package pictureboard.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pictureboard.api.service.PictureTagService;

import java.util.List;

@SpringBootTest
class PictureTagRepositoryTest {

    @Autowired
    PictureTagRepository pictureTagRepository;

    @Autowired
    PictureTagService pictureTagService;

    @Test
    public void findTitlesTest() {
        System.out.println("==========================");
    }
}