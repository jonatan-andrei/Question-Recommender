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
@Table(name = "tag")
public class Tag {

    @Id
    @SequenceGenerator(name = "tagSeq", sequenceName = "tag_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSeq")
    @NotNull
    @Column(name = "tag_id")
    private Long tagId;

    @NotNull
    @Column(name = "name", unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "active")
    private boolean active;

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
}
