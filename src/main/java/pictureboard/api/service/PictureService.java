package pictureboard.api.service;

import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.Img;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.domain.constant.PictureType;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.dto.PictureDto;
import pictureboard.api.form.PictureSearchCond;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.PictureRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureService {

    private final AccountRepository accountRepository;
    private final PictureRepository pictureRepository;
    private final FileService fileService;
    private final PictureTagService pictureTagService;
    private final ModelMapper modelMapper;

    @Value("${file.picture}")
    private String pictureImgPath;

    @Transactional
    public Picture createPicture(String title, String description, MultipartFile pictureFile, PictureType pictureType,
                              Long loginAccountId, List<String> tagTitles) throws IOException {
        Account account = accountRepository.findById(loginAccountId).orElse(null);
        Img pictureImg = fileService.storeFile(pictureFile, pictureImgPath);


        Picture picture = pictureRepository.save(new Picture(title, description, pictureImg, pictureType, account));
        pictureTagService.createPictureTags(picture.getId(), tagTitles);

        return picture;
    }

    public PictureDto makePictureDtoById(Long pictureId) {
        Picture picture = pictureRepository.findPictureForDto(pictureId);
        return makePictureDto(picture);
    }

    public PictureDto makePictureDto(Picture picture) {
        PictureDto pictureDto = modelMapper.map(picture, PictureDto.class);
        AccountDto accountDto = modelMapper.map(picture.getAccount(), AccountDto.class);
        List<String> tagTitles = picture.getPictureTags().stream().map(pt -> pt.getTag().getTitle()).collect(Collectors.toList());

        pictureDto.setAccount(accountDto);
        pictureDto.setTagTitles(tagTitles);

        return pictureDto;
    }

    public List<PictureDto> findByAccountId(Long accountId) {
        return pictureRepository.findByAccountId(accountId).stream()
                .map(this::makePictureDto)
                .collect(Collectors.toList());
    }

    public List<PictureDto> pictureByFollow(Long accountId, int size) {
        List<Picture> pictures = pictureRepository.findPictureByFollow(accountId, size);
        return pictures.stream()
                .map(this::makePictureDto)
                .collect(Collectors.toList());
    }

    public List<PictureDto> pictureOrderByLikes(int size) {
        List<Picture> pictures = pictureRepository.findPictureOrderByLikes(size);
        return pictures.stream()
                .map(this::makePictureDto)
                .collect(Collectors.toList());
    }

    public Page<PictureDto> picturePage(Pageable pageable) {
        QueryResults<Picture> results = pictureRepository.picturePage(pageable);
        List<PictureDto> pictureDtos = results.getResults().stream()
                .map(this::makePictureDto)
                .collect(Collectors.toList());
         return new PageImpl<>(pictureDtos, pageable, results.getTotal());
    }

    public Page<PictureDto> pictureSearchPage(PictureSearchCond pictureSearchCond, Pageable pageable) {
        QueryResults<Picture> results = pictureRepository.pictureSearchPage(pictureSearchCond, pageable);
        List<PictureDto> pictureDtos = results.getResults().stream()
                .map(this::makePictureDto)
                .collect(Collectors.toList());
        return new PageImpl<>(pictureDtos, pageable, results.getTotal());
    }

    @Transactional
    public PictureDto updateDescription(Long loginAccountId, Long pictureId, String description) {
        Picture picture = pictureRepository.findById(pictureId).orElse(null);
        if (picture.getAccount().getId() != loginAccountId) {
            throw new RuntimeException();
        }
        picture.updateDescription(description);
        return makePictureDto(picture);
    }

    public List<PictureDto> findByAccountLikes(Long accountId) {
        List<Picture> pictures = pictureRepository.findByAccountLikes(accountId);
        return pictures.stream()
                .map(this::makePictureDto)
                .collect(Collectors.toList());
    }
}
