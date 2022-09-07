package jonatan.andrei.utils;

import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class TagTestUtils {

    @Inject
    TagRepository tagRepository;

    public Tag saveWithName(String name) {
        return tagRepository.save(Tag.builder()
                .name(name)
                .active(true)
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
                .build());
    }
}
