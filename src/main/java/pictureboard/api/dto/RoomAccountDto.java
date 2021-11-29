package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import pictureboard.api.domain.Img;

@Data
public class RoomAccountDto {
    
    private Long id;

    private String nickname;

    private Img profileImg;

    private int activeFollowCount;

    private int passiveFollowCount;

    private boolean isMyAccount;

    public RoomAccountDto(Long id, String nickname, Img profileImg, int activeFollowCount, int passiveFollowCount, boolean isMyAccount) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.activeFollowCount = activeFollowCount;
        this.passiveFollowCount = passiveFollowCount;
        this.isMyAccount = isMyAccount;
    }
}
