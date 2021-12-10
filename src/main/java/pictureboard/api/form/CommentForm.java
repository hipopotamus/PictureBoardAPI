package pictureboard.api.form;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentForm {

    @NotNull
    private Long pictureId;

    @NotEmpty
    private String content;
}
