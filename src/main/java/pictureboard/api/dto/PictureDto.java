package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;
import pictureboard.api.domain.constant.PictureType;
import pictureboard.api.domain.entity.PictureTag;

import java.util.List;

@Data
public class PictureDto {

    private Long id;

    private String title;

    private String description;

    private Img pictureImg;

    private int likeCount;

    private PictureType pictureType;

    private AccountDto account;

    private List<String> tagTitles;
}
