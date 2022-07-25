package jonatan.haas.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="word")
public class Word {

    @Id
    @SequenceGenerator(name = "wordSeq", sequenceName = "word_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordSeq")
    @NotNull
    @Column(name = "word_id")
    private Long wordId;

    @NotNull
    @Column(name = "word")
    @Size(min = 1, max = 100)
    private String word;

    @NotNull
    @Column(name = "question_count")
    private Integer questionCount;
}
