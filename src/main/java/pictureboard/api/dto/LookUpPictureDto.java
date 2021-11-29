package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;
import pictureboard.api.domain.PictureType;

import java.util.List;

@Data
public class LookUpPictureDto {

    private Long id;

    private String title;

    private String description;

    private Img pictureImg;

    private int likeCount;

    private PictureType pictureType;

    private LookUpPictureAccountDto lookUpPictureAccountDto;

    boolean isMyPicture;

    boolean likesCheck;

    boolean followCheck;

    List<String> Tags;

    @QueryProjection
    public LookUpPictureDto(Long id, String title, String description, Img pictureImg, int likeCount,
                            PictureType pictureType, LookUpPictureAccountDto lookUpPictureAccountDto,
                            boolean isMyPicture, boolean likesCheck, boolean followCheck) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pictureImg = pictureImg;
        this.likeCount = likeCount;
        this.pictureType = pictureType;
        this.lookUpPictureAccountDto = lookUpPictureAccountDto;
        this.isMyPicture = isMyPicture;
        this.likesCheck = likesCheck;
        this.followCheck = followCheck;
    }
}
