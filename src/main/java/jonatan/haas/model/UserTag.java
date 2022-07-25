package jonatan.haas.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_tag")
public class UserTag {

    @Id
    @SequenceGenerator(name = "userTagSeq", sequenceName = "user_tag_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userTagSeq")
    @NotNull
    @Column(name = "user_tag_id")
    private Long userTagId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "tag_id")
    private Long tagId;

    @NotNull
    @Column(name = "number_questions_asked")
    private Integer numberQuestionsAsked;

    @NotNull
    @Column(name = "number_questions_viewed")
    private Integer numberQuestionsViewed;

    @NotNull
    @Column(name = "number_questions_voted")
    private Integer numberQuestionsVoted;

    @NotNull
    @Column(name = "number_questions_answered")
    private Integer numberQuestionsAnswered;

    @NotNull
    @Column(name = "number_questions_followed")
    private Integer numberQuestionsFollowed;

    @NotNull
    @Column(name = "number_answers_voted")
    private Integer numberAnswersVoted;

    @NotNull
    @Column(name = "number_comments_voted")
    private Integer numberCommentsVoted;

    @NotNull
    @Column(name = "explicit_recommendation")
    private boolean explicitRecommendation;

    @NotNull
    @Column(name = "ignored")
    private boolean ignored;
}

