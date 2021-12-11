package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pictureboard.api.argumentresolver.LoginAccount;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.dto.PictureDto;
import pictureboard.api.dto.Result;
import pictureboard.api.exception.IllegalFormException;
import pictureboard.api.form.PictureDescriptionForm;
import pictureboard.api.form.PictureForm;
import pictureboard.api.form.PictureSearchCond;
import pictureboard.api.form.PictureTagForm;
import pictureboard.api.service.PictureService;
import pictureboard.api.service.PictureTagService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"3. Picture"})
@RestController
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;
    private final PictureTagService pictureTagService;

    @ApiOperation(value = "사진 생성", notes = "'사진 정보'를 받아 새로운 사진을 생성합니다.")
    @PostMapping("/picture/create")
    public Object createPicture(@ApiIgnore @LoginAccount Long loginAccountId,
                                @Valid @ModelAttribute PictureForm pictureForm,
                                @ApiIgnore Errors errors) throws IOException {

        if (errors.hasErrors()) {
            return errors.getAllErrors();
        }

        Picture picture = pictureService.createPicture(pictureForm.getTitle(), pictureForm.getDescription(),
                pictureForm.getPictureFile(), pictureForm.getPictureType(), loginAccountId,
                pictureForm.getTagTitles());

        return pictureService.makePictureDtoById(picture.getId());
    }

    @ApiOperation(value = "사진 내용 수정", notes = "'변경할 사진의 아이디'와 '내용'을 받아 사진의 내용을 수정합니다.")
    @PostMapping("/picture/description")
    public PictureDto updateDescription(@ApiIgnore @LoginAccount Long loginAccountId,
                                        @Valid @RequestBody PictureDescriptionForm pictureDescriptionForm,
                                        @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }
        return pictureService.updateDescription(loginAccountId, pictureDescriptionForm.getId(),
                pictureDescriptionForm.getDescription());
    }

    @ApiOperation(value = "사진 태그 추가", notes = "'사진의 아이디'와 '하나 이상의 태그'를 받아 사진에 추가합니다.")
    @PostMapping("/picture/tag")
    public PictureDto updatePictureTags(@Valid @RequestBody PictureTagForm pictureTagForm,
                                        @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }
        pictureTagService.createPictureTags(pictureTagForm.getId(), pictureTagForm.getTagTitles());
        return pictureService.makePictureDtoById(pictureTagForm.getId());
    }

    @ApiOperation(value = "사진 태그 삭제", notes = "'사진의 아이디'와 '하나 이상의 태그'를 받고 사진에서 해당 태그들을 삭제합니다.")
    @DeleteMapping("/picture/tag")
    public String deletePictureTags(@Valid @RequestBody PictureTagForm pictureTagForm,
                                    @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalFormException(errors);
        }
        pictureTagService.deletePictureTags(pictureTagForm.getId(), pictureTagForm.getTagTitles());
        return "delete pictureTags success";
    }

    @ApiOperation(value = "사진 조회", notes = "'사진 아이디'를 받아 해당 사진을 조회합니다.")
    @GetMapping("/picture/{pictureId}")
    public PictureDto lookUpPicture(@PathVariable Long pictureId) {
        return pictureService.makePictureDtoById(pictureId);
    }

    @ApiOperation(value = "회원의 사진 조회", notes = "'회원 아이디'를 받아 해당 회원이 등록한 모든 사진을 조회합니다.")
    @GetMapping("/picture/account/{accountId}")
    public List<PictureDto> pictureByAccountId(@PathVariable("accountId") Long accountId) {
        return pictureService.findByAccountId(accountId);
    }

    @ApiOperation(value = "팔로우한 회원의 사진 조회", notes = "'회원 아이디'와 '크기'를 받습니다.\n" +
            "해당 회원이 팔로우한 회원들의 사진을 크기 만큼 조회합니다.")
    @GetMapping("/picture/follow")
    public Result<List<PictureDto>> findPictureByFollow(Long accountId, int size) {
        List<PictureDto> pictureDtos = pictureService.pictureByFollow(accountId, size);
        Result<List<PictureDto>> result = new Result<>();
        result.setData(pictureDtos);
        return result;
    }

    @ApiOperation(value = "좋아요 순 사진 조회", notes = "'크기'를 받아 좋아요 순으로 사진을 크기만큼 조회합니다.")
    @GetMapping("/picture/likes")
    public Result<List<PictureDto>> findPictureOrderByLikes(int size) {

        List<PictureDto> pictureDtos = pictureService.pictureOrderByLikes(size);
        Result<List<PictureDto>> result = new Result<>();
        result.setData(pictureDtos);
        return result;
    }

    @ApiOperation(value = "사진 페이지 조회", notes = "'페이지 번호'와 '페이지 크기'를 받아서 사진을 페이지로 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", dataType = "int", paramType = "query")})
    @GetMapping("/picture/page")
    public Page<PictureDto> picturePage(@ApiIgnore Pageable pageable) {
        return pictureService.picturePage(pageable);
    }

    @ApiOperation(value = "사진 검색(페이지로 조회)", notes = "'타이틀+태그', '타이틀', '태그', '회원 닉네임'을 검색 조건으로 받을 수 있습니다.\n" +
            "'페이지 번호'와 '페이지 크기'를 받아서 사진을 페이지로 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", dataType = "int", paramType = "query")})
    @GetMapping("/picture/page/search")
    public Page<PictureDto> pictureSearchBoard(PictureSearchCond pictureSearchCond, @ApiIgnore Pageable pageable) {
        return pictureService.pictureSearchPage(pictureSearchCond, pageable);
    }

    @ApiOperation(value = "좋아요 한 사진 조회", notes = "회원 아이디를 받아 해당 회원이 좋아요 한 사진들을 조회합니다.")
    @GetMapping("/picture/likes/{accountId}")
    public Result<List<PictureDto>> findByAccountLikes(@PathVariable("accountId") Long accountId) {
        List<PictureDto> pictureDtoList = pictureService.findByAccountLikes(accountId);
        Result<List<PictureDto>> result = new Result<>();
        result.setData(pictureDtoList);
        return result;
    }

    @ApiOperation(value = "사진 삭제", notes = "'사진 아이디'를 받아 해당 사진을 삭제 합니다.")
    @DeleteMapping("/picture/{pictureId}")
    public String deletePicture(@PathVariable("pictureId") Long pictureId) {
        pictureService.deletePicture(pictureId);
        return "delete picture success";
    }
}
