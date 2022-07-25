package jonatan.haas.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name="post_id")
@Table(name="answer")
public class Answer extends Post {

    @NotNull
    @Column(name = "content")
    @Size(min = 1, max = 4000)
    private String content;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "best_answer")
    private boolean bestAnswer;

}
