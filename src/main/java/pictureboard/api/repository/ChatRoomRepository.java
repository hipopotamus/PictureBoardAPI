package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pictureboard.api.domain.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
