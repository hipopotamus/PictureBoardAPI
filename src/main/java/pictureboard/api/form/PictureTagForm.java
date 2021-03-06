package pictureboard.api.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PictureTagForm {

    @NotNull
    private Long id;

    private List<String> tagTitles;
}
