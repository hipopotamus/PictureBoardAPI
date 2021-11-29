package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;

@Data
public class PicturePageAccountDto {

    private Long id;

    private String nickname;

    private Img profileImg;

    @QueryProjection
    public PicturePageAccountDto(Long id, String nickname, Img profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
