package jonatan.andrei.factory;

import jonatan.andrei.model.QuestionView;

public class QuestionViewFactory {

    public static QuestionView newQuestionView(Long questionId, Long userId) {
        return QuestionView.builder()
                .questionId(questionId)
                .userId(userId)
                .numberOfViews(0)
                .numberOfRecommendationsInList(0)
                .numberOfRecommendationsInEmail(0)
                .recommendedInNotification(false)
                .build();
    }
}
