package pictureboard.api.form;

import lombok.Data;

@Data
public class PictureSearchCond {

    private String titleOrTag;

    private String nickname;
}
