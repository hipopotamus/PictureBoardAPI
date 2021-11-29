package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.dto.LookUpPictureDto;
import pictureboard.api.dto.PicturePageDto;
import pictureboard.api.dto.Result;
import pictureboard.api.form.CreatePictureForm;
import pictureboard.api.form.LoginAccountForm;
import pictureboard.api.form.PictureSearchCond;
import pictureboard.api.service.PictureService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/picture/create")
    public Object createPicture(@LoginAccount LoginAccountForm loginAccountForm,
                                @Valid @ModelAttribute CreatePictureForm createPictureForm, Errors errors) throws IOException {

        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }

        pictureService.createPicture(createPictureForm.getTitle(), createPictureForm.getDescription(),
                createPictureForm.getPictureFile(), createPictureForm.getPictureType(), loginAccountForm.getUsername(),
                createPictureForm.getTagTitles());

        return "createPicture Success";
    }

    @GetMapping("/picture/{pictureId}")
    public LookUpPictureDto lookUpPicture(@LoginAccount LoginAccountForm loginAccountForm, @PathVariable Long pictureId) {
        return pictureService.makeLookUpPictureDto(pictureId, loginAccountForm.getId());
    }

    @GetMapping("/picture/page")
    public Page<PicturePageDto> pictureBoard(@PageableDefault(size = 20) Pageable pageable) {
        return pictureService.picturePage(pageable);
    }

    @GetMapping("/picture/search/page")
    public Result<List> pictureSearchBoard(PictureSearchCond pictureSearchCond,
                                                   @PageableDefault(size = 20) Pageable pageable) {
        Result<List> result = new Result<List>(new ArrayList());
        List data = result.getData();

        Page<PicturePageDto> picturePageDtos = pictureService.pictureSearchPage(pictureSearchCond, pageable);
        data.add(picturePageDtos);
        data.add(pictureSearchCond);
        return result;
    }
}
