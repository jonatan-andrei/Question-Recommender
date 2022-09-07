package jonatan.andrei.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="test_result")
public class TestResult {

    @Id
    @SequenceGenerator(name = "testResultSeq", sequenceName = "test_result_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testResultSeq")
    @NotNull
    @Column(name = "test_result_id")
    private Long testResultId;

    @NotNull
    @Column(name = "dump_name")
    private String dumpName;

    @NotNull
    @Column(name = "integrated_dump_percentage")
    private BigDecimal integratedDumpPercentage;

    @NotNull
    @Column(name = "days_after_dump_considered")
    private Integer daysAfterDumpConsidered;

    @NotNull
    @Column(name = "settings")
    private String settings;

    @NotNull
    @Column(name = "number_of_users")
    private Integer numberOfUsers;

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
    @Column(name = "test_date")
    private LocalDateTime testDate;

}
