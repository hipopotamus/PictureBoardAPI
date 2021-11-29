package pictureboard.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pictureboard.api.dto.LookUpPictureDto;
import pictureboard.api.dto.PicturePageDto;
import pictureboard.api.dto.RoomDto;
import pictureboard.api.dto.RoomPictureDto;
import pictureboard.api.form.PictureSearchCond;

public interface PictureRepositoryCustom {

    LookUpPictureDto makeLookUpPictureDto(Long pictureId, Long LoginAccountId);

    Page<PicturePageDto> picturePage(Pageable pageable);

    Page<PicturePageDto> pictureSearchPage(PictureSearchCond pictureSearchCond, Pageable pageable);
}
