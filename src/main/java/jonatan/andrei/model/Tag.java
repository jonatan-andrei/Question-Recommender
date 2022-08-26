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
@Table(name = "tag")
public class Tag {

    @Id
    @SequenceGenerator(name = "tagSeq", sequenceName = "tag_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSeq")
    @NotNull
    @Column(name = "tag_id")
    private Long tagId;

    @NotNull
    @Column(name = "name", unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "active")
    private boolean active;

    @NotNull
    @Column(name = "question_count")
    private Integer questionCount;
}
