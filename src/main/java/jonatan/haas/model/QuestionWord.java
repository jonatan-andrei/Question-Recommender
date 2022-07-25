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
@Table(name="question_word")
public class QuestionWord {

    @Id
    @SequenceGenerator(name = "questionWordSeq", sequenceName = "question_word_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionWordSeq")
    @NotNull
    @Column(name = "question_word_id")
    private Long questionWordId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "word_id")
    private Long wordId;
}
