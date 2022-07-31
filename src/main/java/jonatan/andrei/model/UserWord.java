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
@Table(name="user_word")
public class UserWord {

    @Id
    @SequenceGenerator(name = "userWordSeq", sequenceName = "user_word_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userWordSeq")
    @NotNull
    @Column(name = "user_word_id")
    private Long userWordId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "word_id")
    private Long wordId;

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

}

