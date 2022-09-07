package jonatan.andrei.factory;

import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.model.TotalActivitySystem;

import java.math.BigDecimal;

public class TotalActivitySystemFactory {

    public static TotalActivitySystem newTotalActivitySystem(PostClassificationType postClassificationType){
        return TotalActivitySystem.builder()
                .postClassificationType(postClassificationType)
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
                .build();
    }
}
