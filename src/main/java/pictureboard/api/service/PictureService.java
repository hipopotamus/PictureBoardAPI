package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Img;
import pictureboard.api.domain.Picture;
import pictureboard.api.domain.PictureType;
import pictureboard.api.dto.LookUpPictureAccountDto;
import pictureboard.api.dto.LookUpPictureDto;
import pictureboard.api.dto.PicturePageDto;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.form.PictureSearchCond;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.PictureRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureService {

    private final AccountRepository accountRepository;
    private final PictureRepository pictureRepository;
    private final FileService fileService;
    private final PictureTagService pictureTagService;

    @Value("${file.picture}")
    private String pictureImgPath;

    @Transactional
    public void createPicture(String title, String description, MultipartFile pictureFile, PictureType pictureType,
                              String username, List<String> tagTitles) throws IOException {
        Account account = accountRepository.findByUsername(username);
        Img pictureImg = fileService.storeFile(pictureFile, pictureImgPath);


        Picture picture = pictureRepository.save(new Picture(title, description, pictureImg, pictureType, account));
        pictureTagService.createPictureTags(picture.getId(), tagTitles);
    }

    public LookUpPictureDto makeLookUpPictureDto(Long pictureId, Long loginAccountId) {
        LookUpPictureDto lookUpPictureDto = pictureRepository.makeLookUpPictureDto(pictureId, loginAccountId);
        List<String> tags = pictureTagService.makeTagTitles(pictureId);
        lookUpPictureDto.setTags(tags);

        return lookUpPictureDto;
    }

    public Page<PicturePageDto> picturePage(Pageable pageable) {
        return pictureRepository.picturePage(pageable);
    }

    public Page<PicturePageDto> pictureSearchPage(PictureSearchCond pictureSearchCond, Pageable pageable) {
        return pictureRepository.pictureSearchPage(pictureSearchCond, pageable);
    }
}
