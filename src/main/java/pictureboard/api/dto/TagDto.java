package pictureboard.api.dto;

import lombok.Data;

@Data
public class TagDto {

    private Long id;

    private String title;

    private int relatedPictureCount;
}
