package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import pictureboard.api.domain.constant.Gender;
import pictureboard.api.domain.Img;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private String username;

    private String nickname;

    private Img profileImg;

    private Gender gender;

    private LocalDate birthDate;

    private int activeFollowCount;

    private int passiveFollowCount;

    @QueryProjection
    public AccountDto(Long id, String username, String nickname, Img profileImg, Gender gender, LocalDate birthDate, int activeFollowCount, int passiveFollowCount) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.gender = gender;
        this.birthDate = birthDate;
        this.activeFollowCount = activeFollowCount;
        this.passiveFollowCount = passiveFollowCount;
    }
}
