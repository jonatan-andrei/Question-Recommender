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
@Table(name="question_tag")
public class QuestionTag {

    @Id
    @SequenceGenerator(name = "questionTagSeq", sequenceName = "question_tag_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionTagSeq")
    @NotNull
    @Column(name = "question_tag_id")
    private Long questionTagId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "tag_id")
    private Long tagId;
}
