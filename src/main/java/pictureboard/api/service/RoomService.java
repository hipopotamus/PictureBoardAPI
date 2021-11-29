package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Picture;
import pictureboard.api.dto.RoomAccountDto;
import pictureboard.api.dto.RoomDto;
import pictureboard.api.dto.RoomPictureDto;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.PictureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final AccountRepository accountRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public RoomDto makeRoomDto(Long loginAccountId, Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        List<Picture> pictures = pictureRepository.findByAccount(account);
        boolean isMyAccount = loginAccountId.equals(accountId);

        RoomAccountDto roomAccountDto = modelMapper.map(account, RoomAccountDto.class);
        List<RoomPictureDto> pictureDtos = pictures.stream().map(p -> modelMapper.map(p, RoomPictureDto.class)).collect(Collectors.toList());
        roomAccountDto.setMyAccount(isMyAccount);
        return new RoomDto(roomAccountDto, pictureDtos);
    }

}
