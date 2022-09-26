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
@Table(name = "recommended_email_question")
public class RecommendedEmailQuestion {

    @Id
    @SequenceGenerator(name = "recommendedEmailQuestionSeq", sequenceName = "recommended_email_question_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendedEmailQuestionSeq")
    @NotNull
    @Column(name = "recommended_email_question_id")
    private Long recommendedEmailQuestionId;

    @NotNull
    @Column(name = "recommended_email_id")
    private Long recommendedEmailId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "integration_question_id")
    private String integrationQuestionId;

    @NotNull
    @Column(name = "score")
    private BigDecimal score;

}

