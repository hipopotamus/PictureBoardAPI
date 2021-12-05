package pictureboard.api.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    private String content;

    private AccountDto accountDto;


}
