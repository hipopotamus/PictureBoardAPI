package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Comment;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.dto.CommentDto;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.repository.CommentRepository;
import pictureboard.api.repository.PictureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PictureRepository pictureRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Comment createComment(Long pictureId, Long accountId, String content) {
        Picture picture = pictureRepository.findById(pictureId).orElseThrow(RuntimeException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(RuntimeException::new);
        return commentRepository.save(new Comment(picture, account, content));
    }

    public CommentDto makeCommentDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        AccountDto accountDto = modelMapper.map(comment.getAccount(), AccountDto.class);
        commentDto.setAccountDto(accountDto);
        return commentDto;
    }

    public List<CommentDto> findComment(Long pictureId) {
        List<Comment> comments = commentRepository.findByPictureFetchAccount(pictureId);
        return comments.stream()
                .map(this::makeCommentDto)
                .collect(Collectors.toList());
    }

}
