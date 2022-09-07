package jonatan.andrei.factory;

import jonatan.andrei.dto.QuestionsAnsweredByUserDto;
import jonatan.andrei.dto.QuestionsAnsweredByUserResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionsAnsweredByUserFactory {

    public static QuestionsAnsweredByUserResponseDto newQuestionsAnsweredByUser(String integrationUserId, List<QuestionsAnsweredByUserDto> questions) {
        return QuestionsAnsweredByUserResponseDto.builder()
                .integrationUserId(integrationUserId)
                .dateFirstAnswer(Collections.min(questions.stream().map(QuestionsAnsweredByUserDto::getAnswerDate).collect(Collectors.toList())))
                .questions(questions.stream().map(q -> QuestionsAnsweredByUserResponseDto.QuestionAnsweredResponseDto.builder()
                        .integrationQuestionId(q.getIntegrationQuestionId())
                        .followers(q.getFollowers())
                        .bestAnswerId(q.getBestAnswerId())
                        .answers(q.getAnswers())
                        .tags(q.getTags())
                        .answerDate(q.getAnswerDate())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
