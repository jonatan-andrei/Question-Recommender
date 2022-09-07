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
@Table(name = "test_result_user")
public class TestResultUser {

    @Id
    @SequenceGenerator(name = "testResultUserSeq", sequenceName = "test_result_user_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testResultUserSeq")
    @NotNull
    @Column(name = "test_result_user_id")
    private Long testResultUserId;

    @NotNull
    @Column(name = "test_result_id")
    private Long testResultId;

    @NotNull
    @Column(name = "integration_user_id")
    private String integrationUserId;

    @NotNull
    @Column(name = "number_of_questions")
    private Integer numberOfQuestions;

    @NotNull
    @Column(name = "number_of_recommended_questions")
    private Integer numberOfRecommendedQuestions;

    @NotNull
    @Column(name = "percentage_of_correct_recommendations")
    private BigDecimal percentageOfCorrectRecommendations;

    @NotNull
    @Column(name = "error")
    private boolean error;
}
