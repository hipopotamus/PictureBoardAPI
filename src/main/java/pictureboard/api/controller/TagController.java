package pictureboard.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.dto.Result;
import pictureboard.api.dto.TagDto;
import pictureboard.api.service.TagService;

import java.util.List;

@Api(tags = {"7. Tag"})
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @ApiOperation(value = "태그 조회", notes = "모든 태그를 조회합니다.")
    @GetMapping("/tag")
    public Result<List<TagDto>> findAll() {
        List<TagDto> allAndOrderByRelatedPictureCount = tagService.findAllAndOrderByRelatedPictureCount();
        Result<List<TagDto>> result = new Result<>(allAndOrderByRelatedPictureCount);
        return result;
    }

}
