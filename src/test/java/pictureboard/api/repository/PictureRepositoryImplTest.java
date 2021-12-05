package pictureboard.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.dto.PictureDto;
import pictureboard.api.service.PictureService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PictureRepositoryImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    PictureService pictureService;

    @Test
    public void makeLookUpPictureTest() {
        Picture picture = pictureRepository.findById(3L).orElse(null);

        Picture picture1 = pictureRepository.findPictureForDto(3L);
        PictureDto pictureTestByDto = pictureService.makePictureDtoById(picture1.getId());

//        PictureDto pictureDto = pictureService.makePictureDto(3L, 1L);
        assertThat(pictureTestByDto.getDescription()).isEqualTo(picture.getDescription());
        assertThat(pictureTestByDto.getTitle()).isEqualTo(picture.getTitle());
        assertThat(pictureTestByDto.getLikeCount()).isEqualTo(picture.getLikeCount());
        System.out.println(pictureTestByDto.getPictureImg());
        System.out.println(pictureTestByDto.getTagTitles());
    }
}