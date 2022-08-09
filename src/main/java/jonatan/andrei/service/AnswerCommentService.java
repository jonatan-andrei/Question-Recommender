package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.AnswerComment;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.AnswerCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AnswerCommentService {

    @Inject
    AnswerCommentRepository answerCommentRepository;

    public AnswerComment save(CreatePostRequestDto createPostRequestDto, User user) {
        return null;
    }

    public AnswerComment update(AnswerComment existingAnswerComment, UpdatePostRequestDto updatePostRequestDto) {
        return null;
    }
}
