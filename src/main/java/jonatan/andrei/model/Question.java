package jonatan.andrei.model;


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
@Table(name="question")
public class Question extends Post {

    @NotNull
    @Column(name = "title")
    @Size(min = 1, max = 1000)
    private String title;

    @NotNull
    @Column(name = "description")
    @Size(min = 1, max = 4000)
    private String description;

    @NotNull
    @Column(name = "followers")
    private Integer followers;

    @NotNull
    @Column(name = "duplicate_question")
    private boolean duplicateQuestion;

    @Column(name = "duplicate_question_id")
    private Long duplicateQuestionId;

    @Column(name = "url")
    @Size(min = 1, max = 500)
    private String url;
}