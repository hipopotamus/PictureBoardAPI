package pictureboard.api.form;

import lombok.Data;

@Data
public class PictureSearchCond {

    private String titleOrTag;

    private String title;

    private String tagTitle;

    private String nickname;
}
