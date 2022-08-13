package jonatan.andrei.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder
@PrimaryKeyJoinColumn(name="post_id")
@Table(name="answer_comment")
public class AnswerComment extends Post {

    @NotNull
    @Column(name = "content")
    @Size(min = 1, max = 4000)
    private String content;

    @NotNull
    @Column(name = "answer_id")
    private Long answerId;

}
