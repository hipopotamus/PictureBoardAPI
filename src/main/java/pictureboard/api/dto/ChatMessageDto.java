package pictureboard.api.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private String roomId;
    private String writer;
    private String message;
}
