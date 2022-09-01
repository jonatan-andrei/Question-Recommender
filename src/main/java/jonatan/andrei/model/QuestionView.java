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
@Table(name = "question_view")
public class QuestionView {

    @Id
    @SequenceGenerator(name = "questionViewSeq", sequenceName = "question_view_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionViewSeq")
    @NotNull
    @Column(name = "question_view_id")
    private Long questionViewId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "number_of_views")
    private Integer numberOfViews;

    @NotNull
    @Column(name = "number_of_recommendations_in_list")
    private Integer numberOfRecommendationsInList;

    @NotNull
    @Column(name = "number_of_recommendations_in_email")
    private Integer numberOfRecommendationsInEmail;

    @NotNull
    @Column(name = "notified_question")
    private boolean notifiedQuestion;

}
