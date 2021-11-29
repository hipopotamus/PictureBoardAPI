package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;

import java.time.LocalDateTime;

@Data
public class RoomPictureDto {

    private Long id;

    private String title;

    private Img pictureImg;

    private LocalDateTime lastModifiedDate;

    public RoomPictureDto(Long id, String title, Img pictureImg, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.pictureImg = pictureImg;
        this.lastModifiedDate = lastModifiedDate;
    }
}
