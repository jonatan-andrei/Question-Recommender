package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.Answer;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.AnswerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class AnswerService {

    @Inject
    AnswerRepository answerRepository;

    public Answer save(CreatePostRequestDto createPostRequestDto, User user) {
        return null;
    }

    public Answer update(Answer existingAnswer, UpdatePostRequestDto updatePostRequestDto) {
        return null;
    }

    @Transactional
    public void registerBestAnswer(Long postId, boolean selected) {
        answerRepository.registerBestAnswer(postId, selected);
    }
}
