package pictureboard.api.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.domain.constant.Gender;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class AccountForm {

    @NotEmpty
    @Length(min = 4, max = 20)
    private String username;

    @NotEmpty
    @Length(min = 4, max = 30)
    private String password;

    @NotEmpty
    @Length(min = 4, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{4,20}$")
    private String nickname;

    @NotNull
    private MultipartFile profileFile;

    @NotNull
    private Gender gender;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
