package jonatan.andrei.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_category")
public class UserCategory {

    @Id
    @SequenceGenerator(name = "userCategorySeq", sequenceName = "user_category_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userCategorySeq")
    @NotNull
    @Column(name = "user_category_id")
    private Long userCategoryId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "number_questions_asked")
    private BigDecimal numberQuestionsAsked;

    @NotNull
    @Column(name = "number_questions_viewed")
    private BigDecimal numberQuestionsViewed;

    @NotNull
    @Column(name = "number_questions_answered")
    private BigDecimal numberQuestionsAnswered;

    @NotNull
    @Column(name = "number_questions_commented")
    private BigDecimal numberQuestionsCommented;

    @NotNull
    @Column(name = "number_questions_followed")
    private BigDecimal numberQuestionsFollowed;

    @NotNull
    @Column(name = "number_questions_upvoted")
    private BigDecimal numberQuestionsUpvoted;

    @NotNull
    @Column(name = "number_questions_downvoted")
    private BigDecimal numberQuestionsDownvoted;

    @NotNull
    @Column(name = "number_answers_upvoted")
    private BigDecimal numberAnswersUpvoted;

    @NotNull
    @Column(name = "number_answers_downvoted")
    private BigDecimal numberAnswersDownvoted;

    @NotNull
    @Column(name = "number_comments_upvoted")
    private BigDecimal numberCommentsUpvoted;

    @NotNull
    @Column(name = "number_comments_downvoted")
    private BigDecimal numberCommentsDownvoted;

    @NotNull
    @Column(name = "explicit_recommendation")
    private boolean explicitRecommendation;

    @NotNull
    @Column(name = "ignored")
    private boolean ignored;
}