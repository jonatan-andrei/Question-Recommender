package jonatan.andrei.service;

import jonatan.andrei.dto.QuestionFollowerRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.QuestionFollowerFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionFollower;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionFollowerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

import static java.util.Objects.isNull;

@ApplicationScoped
public class QuestionFollowerService {

    @Inject
    QuestionFollowerRepository questionFollowerRepository;

    public Question registerQuestionFollower(QuestionFollowerRequestDto questionFollowerRequestDto, User user, Question question) {
        Optional<QuestionFollower> existingQuestionFollower = findByUserIdAndQuestionId(user, question);
        if (existingQuestionFollower.isEmpty() && questionFollowerRequestDto.isFollowed()) {
            questionFollowerRepository.save(QuestionFollowerFactory.newQuestionFollower(questionFollowerRequestDto, user, question));
            question.setFollowers(question.getFollowers() + 1);
        } else if (existingQuestionFollower.isPresent() && !questionFollowerRequestDto.isFollowed()) {
            questionFollowerRepository.delete(existingQuestionFollower.get());
            question.setFollowers(question.getFollowers() - 1);
        }
        return question;
    }

    public Optional<QuestionFollower> findByUserIdAndQuestionId(User user, Question question) {
        return questionFollowerRepository.findByUserIdAndQuestionId(user.getUserId(), question.getPostId());
    }

    public void validateQuestionFollowerRequest(QuestionFollowerRequestDto questionFollowerRequestDto) {
        if (isNull(questionFollowerRequestDto.getIntegrationQuestionId())) {
            throw new RequiredDataException("Attribute 'integrationQuestionId' is required");
        }
        if (isNull(questionFollowerRequestDto.getIntegrationUserId())) {
            throw new RequiredDataException("Attribute 'integrationUserId' is required");
        }
    }
}
