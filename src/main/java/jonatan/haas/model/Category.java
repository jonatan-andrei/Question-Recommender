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
@Table(name="category")
public class Category {

    @Id
    @SequenceGenerator(name = "categorySeq", sequenceName = "category_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeq")
    @NotNull
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "integration_category_id")
    @Size(min = 1, max = 100)
    private String integrationCategoryId;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @NotNull
    @Column(name = "name")
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "description")
    @Size(min = 1, max = 1000)
    private String description;

    @NotNull
    @Column(name = "active")
    private boolean active;

    @NotNull
    @Column(name = "question_count")
    private Integer questionCount;
}
