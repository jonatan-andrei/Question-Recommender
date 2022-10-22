package jonatan.andrei.factory;

import jonatan.andrei.model.RecommendedList;

import java.time.LocalDateTime;

public class RecommendedListFactory {

    public static RecommendedList newRecommendedList(Integer lengthQuestionListPage,
                                                     Long userId,
                                                     Integer totalPages,
                                                     Integer totalQuestions,
                                                     LocalDateTime dateOfRecommendations,
                                                     Integer totalPagesWithRecommendedQuestions,
                                                     LocalDateTime minimumDateForRecommendedQuestions) {
        return RecommendedList.builder()
                .userId(userId)
                .totalNumberOfPages(totalPages)
                .totalNumberOfQuestions(totalQuestions)
                .listDate(dateOfRecommendations)
                .totalPagesWithRecommendedQuestions(totalPagesWithRecommendedQuestions)
                .minimumDateForRecommendedQuestions(minimumDateForRecommendedQuestions)
                .lengthQuestionListPage(lengthQuestionListPage)
                .build();
    }
}
