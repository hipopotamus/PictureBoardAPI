package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.dto.PictureDto;
import pictureboard.api.dto.Result;
import pictureboard.api.form.CreatePictureForm;
import pictureboard.api.form.PictureSearchCond;
import pictureboard.api.form.PictureTagForm;
import pictureboard.api.service.PictureService;
import pictureboard.api.service.PictureTagService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;
    private final PictureTagService pictureTagService;

    @PostMapping("/picture/create")
    public Object createPicture(@LoginAccount Long loginAccountId,
                                @Valid @ModelAttribute CreatePictureForm createPictureForm, Errors errors) throws IOException {

        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }

        Picture picture = pictureService.createPicture(createPictureForm.getTitle(), createPictureForm.getDescription(),
                createPictureForm.getPictureFile(), createPictureForm.getPictureType(), loginAccountId,
                createPictureForm.getTagTitles());

        return pictureService.makePictureDtoById(picture.getId());
    }

    @GetMapping("/picture/{pictureId}")
    public PictureDto lookUpPicture(@PathVariable Long pictureId) {
        return pictureService.makePictureDtoById(pictureId);
    }

    @GetMapping("/picture/account/{accountId}")
    public List<PictureDto> pictureByAccountId(@PathVariable("accountId") Long accountId) {
        return pictureService.findByAccountId(accountId);
    }

    @GetMapping("/picture/follow")
    public Result<List<PictureDto>> findPictureByFollow(Long accountId, int size) {
        List<PictureDto> pictureDtos = pictureService.pictureByFollow(accountId, size);
        Result<List<PictureDto>> result = new Result<>();
        result.setData(pictureDtos);
        return result;
    }

    @GetMapping("/picture/byLikes")
    public Result<List<PictureDto>> findPictureOrderByLikes(int size) {

        List<PictureDto> pictureDtos = pictureService.pictureOrderByLikes(size);
        Result<List<PictureDto>> result = new Result<>();
        result.setData(pictureDtos);
        return result;
    }

    @GetMapping("/picture/page")
    public Page<PictureDto> picturePage(Pageable pageable) {
        return pictureService.picturePage(pageable);
    }

    @GetMapping("/picture/page/search")
    public Page<PictureDto> pictureSearchBoard(PictureSearchCond pictureSearchCond, Pageable pageable) {
        return pictureService.pictureSearchPage(pictureSearchCond, pageable);
    }

    @PostMapping("/picture/updateDescription/{pictureId}")
    public PictureDto updateDescription(@LoginAccount Long loginAccountId, @PathVariable("pictureId") Long pictureId, String description) {
        return pictureService.updateDescription(loginAccountId, pictureId, description);
    }

    @GetMapping("/picture/likes/{accountId}")
    public Result<List<PictureDto>> findByAccountLikes(@PathVariable("accountId") Long accountId) {
        List<PictureDto> pictureDtoList = pictureService.findByAccountLikes(accountId);
        Result<List<PictureDto>> result = new Result<>();
        result.setData(pictureDtoList);
        return result;
    }

    @PostMapping("/picture/tag/update")
    public PictureDto updatePictureTags(PictureTagForm pictureTagForm) {
        pictureTagService.createPictureTags(pictureTagForm.getId(), pictureTagForm.getTagTitles());
        return pictureService.makePictureDtoById(pictureTagForm.getId());
    }

    @PostMapping("/picture/tag/delete")
    public String deletePictureTags(PictureTagForm pictureTagForm) {
        pictureTagService.deletePictureTag(pictureTagForm.getId(), pictureTagForm.getTagTitles());
        return "delete pictureTags success";
    }
}
