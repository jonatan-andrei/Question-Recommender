package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTagDto {

    private Long tagId;

    private String tagName;

    private BigDecimal numberQuestionsAsked;

    private BigDecimal numberQuestionsViewed;

    private BigDecimal numberQuestionsAnswered;

    private BigDecimal numberQuestionsCommented;

    private BigDecimal numberQuestionsFollowed;

    private BigDecimal numberQuestionsUpvoted;

    private BigDecimal numberQuestionsDownvoted;

    private BigDecimal numberAnswersUpvoted;

    private BigDecimal numberAnswersDownvoted;

    private BigDecimal numberCommentsUpvoted;

    private BigDecimal numberCommentsDownvoted;

}
