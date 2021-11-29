package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;

@Data
public class LookUpPictureAccountDto {

    private Long id;

    private String username;

    private String nickname;

    private Img profileImg;

    private int activeFollowCount;

    private int passiveFollowCount;

    @QueryProjection
    public LookUpPictureAccountDto(Long id, String username, String nickname, Img profileImg, int activeFollowCount, int passiveFollowCount) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.activeFollowCount = activeFollowCount;
        this.passiveFollowCount = passiveFollowCount;
    }
}
