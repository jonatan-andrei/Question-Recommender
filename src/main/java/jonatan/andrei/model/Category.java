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
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(name = "categorySeq", sequenceName = "category_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeq")
    @NotNull
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "integration_category_id", unique = true, length = 100)
    private String integrationCategoryId;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

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
