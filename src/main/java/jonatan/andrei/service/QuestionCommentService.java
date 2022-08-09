package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionComment;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class QuestionCommentService {

    @Inject
    QuestionCommentRepository questionCommentRepository;

    public QuestionComment save(CreatePostRequestDto createPostRequestDto, User user) {
        return null;
    }

    public QuestionComment update(QuestionComment existingQuestionComment, UpdatePostRequestDto updatePostRequestDto) {
        return null;
    }
}
