package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.ChatRoom;
import pictureboard.api.dto.ChatRoomDto;
import pictureboard.api.exception.NotFoundSourceException;
import pictureboard.api.repository.ChatRoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ChatRoomDto createChatRoom(String name) {
        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(name));
        return modelMapper.map(chatRoom, ChatRoomDto.class);
    }

    public List<ChatRoomDto> findAll() {
        return chatRoomRepository.findAll().stream()
                .map(this::makeChatRoomDto)
                .collect(Collectors.toList());

    }

    public ChatRoomDto findById(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id)
                .orElseThrow(() -> new NotFoundSourceException("채팅방을 찾을 수 없습니다."));
        return modelMapper.map(chatRoom, ChatRoomDto.class);
    }

    public ChatRoomDto makeChatRoomDto(ChatRoom chatRoom) {
        return modelMapper.map(chatRoom, ChatRoomDto.class);
    }

}
