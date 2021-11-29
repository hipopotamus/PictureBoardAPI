package pictureboard.api.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Picture;
import pictureboard.api.dto.LookUpPictureDto;
import pictureboard.api.dto.PicturePageDto;
import pictureboard.api.form.PictureSearchCond;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PictureRepositoryImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PictureRepository pictureRepository;

    @Test
    public void makeLookUpPictureTest() {
        Account account = accountRepository.findById(1L).orElse(null);
        Picture picture = pictureRepository.findById(2L).orElse(null);

        LookUpPictureDto lookUpPictureDto = pictureRepository.makeLookUpPictureDto(2L, 3L);
        assertThat(lookUpPictureDto.getDescription()).isEqualTo(picture.getDescription());
        assertThat(lookUpPictureDto.getTitle()).isEqualTo(picture.getTitle());
        assertThat(lookUpPictureDto.getLikeCount()).isEqualTo(picture.getLikeCount());
        assertThat(lookUpPictureDto.getLookUpPictureAccountDto().getUsername()).isEqualTo(account.getUsername());
        assertThat(lookUpPictureDto.getLookUpPictureAccountDto().getActiveFollowCount()).isEqualTo(account.getActiveFollowCount());
        assertThat(lookUpPictureDto.isMyPicture()).isEqualTo(false);
        assertThat(lookUpPictureDto.isFollowCheck()).isEqualTo(true);
        assertThat(lookUpPictureDto.isLikesCheck()).isEqualTo(true);
        System.out.println(lookUpPictureDto.isMyPicture());
        System.out.println(lookUpPictureDto.getPictureImg());
    }

    @Test
    public void pictureSearchPageTest() {
        PictureSearchCond pictureSearchCond = new PictureSearchCond();
        pictureSearchCond.setTitleOrTag("titl");
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<PicturePageDto> picturePageDtos = pictureRepository.pictureSearchPage(pictureSearchCond, pageRequest);
        List<PicturePageDto> content = picturePageDtos.getContent();
        for (PicturePageDto picturePageDto : content) {
            System.out.println(picturePageDto.getId());
        }
    }

}