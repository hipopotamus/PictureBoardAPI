package pictureboard.api.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.domain.PictureType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreatePictureForm {

    @NotEmpty
    private String title;

    private String description;

    @NotNull
    private MultipartFile pictureFile;

    @NotNull
    private PictureType pictureType;

    private List<String> tagTitles;
}
