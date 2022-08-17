package jonatan.andrei.factory;

import jonatan.andrei.dto.QuestionFollowerRequestDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionFollower;
import jonatan.andrei.model.User;

public class QuestionFollowerFactory {

    public static QuestionFollower newQuestionFollower(QuestionFollowerRequestDto questionFollowerRequestDto, User user, Question question) {
        return QuestionFollower.builder()
                .userId(user.getUserId())
                .questionId(question.getPostId())
                .startDate(questionFollowerRequestDto.getStartDate())
                .build();
    }
}
