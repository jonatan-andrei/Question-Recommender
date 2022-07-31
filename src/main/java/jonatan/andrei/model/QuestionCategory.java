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
@Table(name="question_category")
public class QuestionCategory {

    @Id
    @SequenceGenerator(name = "questionCategorySeq", sequenceName = "question_category_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionCategorySeq")
    @NotNull
    @Column(name = "question_category_id")
    private Long questionCategoryId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "category_id")
    private Long categoryId;

}
