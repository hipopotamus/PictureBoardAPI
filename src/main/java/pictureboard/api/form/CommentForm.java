package pictureboard.api.form;


import lombok.Data;

@Data
public class CommentForm {

    private Long pictureId;

    private String content;
}
