package pictureboard.api.form;

import lombok.Data;
import pictureboard.api.domain.Gender;

import java.time.LocalDate;

@Data
public class LoginAccountForm {

    private Long id;

    private String username;

    private String nickname;

    private String profileImgPath;

    private Gender gender;

    private LocalDate birthDate;

    private int activeFollow;

    private int passiveFollow;
}
