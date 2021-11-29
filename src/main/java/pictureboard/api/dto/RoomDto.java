package pictureboard.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class RoomDto {

    private RoomAccountDto roomAccount;

    private List<RoomPictureDto> roomPictures;

    public RoomDto(RoomAccountDto roomAccount, List<RoomPictureDto> roomPictures) {

        this.roomAccount = roomAccount;
        this.roomPictures = roomPictures;
    }
}
