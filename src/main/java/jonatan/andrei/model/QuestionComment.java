package jonatan.andrei.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "post_id")
@Table(name = "question_comment")
public class QuestionComment extends Post {

    @NotNull
    @Column(name = "content", length = 4000)
    private String content;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;
}