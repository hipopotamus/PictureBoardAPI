package pictureboard.api.form;

import lombok.Data;

import java.util.List;

@Data
public class PictureTagForm {

    private Long id;

    private List<String> tagTitles;
}
