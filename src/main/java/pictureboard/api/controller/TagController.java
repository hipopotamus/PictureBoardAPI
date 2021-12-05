package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pictureboard.api.dto.Result;
import pictureboard.api.dto.TagDto;
import pictureboard.api.service.TagService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/tag")
    public Result<List<TagDto>> findAll() {
        List<TagDto> allAndOrderByRelatedPictureCount = tagService.findAllAndOrderByRelatedPictureCount();
        Result<List<TagDto>> result = new Result<>(allAndOrderByRelatedPictureCount);
        return result;
    }

}
