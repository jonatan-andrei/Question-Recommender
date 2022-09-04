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
@Table(name = "question")
public class Question extends Post {

    @NotNull
    @Column(name = "title", length = 1000)
    private String title;

    @NotNull
    @Column(name = "description", length = 24000)
    private String description;

    @NotNull
    @Column(name = "followers")
    private Integer followers;

    @Column(name = "duplicate_question_id")
    private Long duplicateQuestionId;

    @Column(name = "best_answer_id")
    private Long bestAnswerId;

    @Column(name = "url", length = 500)
    private String url;

    @NotNull
    @Column(name = "views")
    private Integer views;

    @NotNull
    @Column(name = "answers")
    private Integer answers;
}
