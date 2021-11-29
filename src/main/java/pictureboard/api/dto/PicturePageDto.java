package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;

import java.time.LocalDateTime;

@Data
public class PicturePageDto {

    private Long id;

    private String title;

    private Img pictureImg;

    private PicturePageAccountDto pictureAccount;

    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public PicturePageDto(Long id, String title, Img pictureImg, PicturePageAccountDto pictureAccount,
                          LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.pictureImg = pictureImg;
        this.pictureAccount = pictureAccount;
        this.lastModifiedDate = lastModifiedDate;
    }
}
