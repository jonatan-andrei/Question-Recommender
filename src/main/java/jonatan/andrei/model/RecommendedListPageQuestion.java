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
@Table(name = "recommended_list_page_question")
public class RecommendedListPageQuestion {

    @Id
    @SequenceGenerator(name = "recommendedListPageQuestionSeq", sequenceName = "recommended_list_page_question_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendedListPageQuestionSeq")
    @NotNull
    @Column(name = "recommended_list_page_question_id")
    private Long recommendedListPageQuestionId;

    @NotNull
    @Column(name = "recommended_list_page_id")
    private Long recommendedListPageId;

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
