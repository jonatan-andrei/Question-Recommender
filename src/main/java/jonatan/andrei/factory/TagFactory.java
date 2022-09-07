package jonatan.andrei.factory;

import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.model.Tag;

import java.math.BigDecimal;

public class TagFactory {

    public static Tag newTag(TagRequestDto tagRequestDto) {
        return Tag.builder()
                .name(tagRequestDto.getName())
                .description(tagRequestDto.getDescription())
                .active(tagRequestDto.isActive())
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

    public static Tag newTag(String tagName) {
        return Tag.builder()
                .name(tagName)
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
                .build();
    }

    public static Tag overwrite(Tag existingTag, TagRequestDto tagRequestDto) {
        existingTag.setActive(tagRequestDto.isActive());
        existingTag.setName(tagRequestDto.getName());
        existingTag.setDescription(tagRequestDto.getDescription());
        return existingTag;
    }
}
