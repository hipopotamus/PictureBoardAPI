package pictureboard.api.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AccountProfileForm {
    private MultipartFile profileFile;
}
