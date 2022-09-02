package jonatan.andrei.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Integer numberQuestionsAsked;

    @NotNull
    @Column(name = "number_questions_viewed")
    private Integer numberQuestionsViewed;

    @NotNull
    @Column(name = "number_questions_answered")
    private Integer numberQuestionsAnswered;

    @NotNull
    @Column(name = "number_questions_commented")
    private Integer numberQuestionsCommented;

    @NotNull
    @Column(name = "number_questions_followed")
    private Integer numberQuestionsFollowed;

    @NotNull
    @Column(name = "number_questions_upvoted")
    private Integer numberQuestionsUpvoted;

    @NotNull
    @Column(name = "number_questions_downvoted")
    private Integer numberQuestionsDownvoted;

    @NotNull
    @Column(name = "number_answers_upvoted")
    private Integer numberAnswersUpvoted;

    @NotNull
    @Column(name = "number_answers_downvoted")
    private Integer numberAnswersDownvoted;

    @NotNull
    @Column(name = "number_comments_upvoted")
    private Integer numberCommentsUpvoted;

    @NotNull
    @Column(name = "number_comments_downvoted")
    private Integer numberCommentsDownvoted;

    @NotNull
    @Column(name = "explicit_recommendation")
    private boolean explicitRecommendation;

    @NotNull
    @Column(name = "ignored")
    private boolean ignored;
}