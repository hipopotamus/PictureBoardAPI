package pictureboard.api.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PictureDescriptionForm {

    @NotNull
    private Long id;

    private String description;
}
