package jonatan.andrei.service;

import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.factory.TotalActivitySystemFactory;
import jonatan.andrei.model.TotalActivitySystem;
import jonatan.andrei.repository.TotalActivitySystemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class TotalActivitySystemService {

    @Inject
    TotalActivitySystemRepository totalActivitySystemRepository;

    public void updateNumberByAction(PostClassificationType postClassificationType, UserActionType userActionType, BigDecimal value) {
        TotalActivitySystem totalActivitySystem = findOrCreateByType(postClassificationType);
        switch (userActionType) {
            case QUESTION_ASKED ->
                    totalActivitySystem.setNumberQuestionsAsked(totalActivitySystem.getNumberQuestionsAsked().add(value));
            case QUESTION_VIEWED ->
                    totalActivitySystem.setNumberQuestionsViewed(totalActivitySystem.getNumberQuestionsViewed().add(value));
            case QUESTION_ANSWERED ->
                    totalActivitySystem.setNumberQuestionsAnswered(totalActivitySystem.getNumberQuestionsAnswered().add(value));
            case QUESTION_COMMENTED ->
                    totalActivitySystem.setNumberQuestionsCommented(totalActivitySystem.getNumberQuestionsCommented().add(value));
            case QUESTION_FOLLOWED ->
                    totalActivitySystem.setNumberQuestionsFollowed(totalActivitySystem.getNumberQuestionsFollowed().add(value));
            case QUESTION_UPVOTED ->
                    totalActivitySystem.setNumberQuestionsUpvoted(totalActivitySystem.getNumberQuestionsUpvoted().add(value));
            case QUESTION_DOWNVOTED ->
                    totalActivitySystem.setNumberQuestionsDownvoted(totalActivitySystem.getNumberQuestionsDownvoted().add(value));
            case ANSWER_UPVOTED ->
                    totalActivitySystem.setNumberAnswersUpvoted(totalActivitySystem.getNumberAnswersUpvoted().add(value));
            case ANSWER_DOWNVOTED ->
                    totalActivitySystem.setNumberAnswersDownvoted(totalActivitySystem.getNumberAnswersDownvoted().add(value));
            case COMMENT_UPVOTED ->
                    totalActivitySystem.setNumberCommentsUpvoted(totalActivitySystem.getNumberCommentsUpvoted().add(value));
            case COMMENT_DOWNVOTED ->
                    totalActivitySystem.setNumberCommentsDownvoted(totalActivitySystem.getNumberCommentsDownvoted().add(value));
        }
        totalActivitySystemRepository.save(totalActivitySystem);
    }

    public TotalActivitySystem findOrCreateByType(PostClassificationType postClassificationType) {
        return totalActivitySystemRepository.findByPostClassificationType(postClassificationType)
                .orElseGet(() -> totalActivitySystemRepository.save(TotalActivitySystemFactory.newTotalActivitySystem(postClassificationType)));
    }

    public void clear() {
        totalActivitySystemRepository.deleteAll();
    }
}
