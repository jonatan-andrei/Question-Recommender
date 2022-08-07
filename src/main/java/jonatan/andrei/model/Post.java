package jonatan.andrei.model;

import jonatan.andrei.domain.PostType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="post")
public abstract class Post {

    @Id
    @SequenceGenerator(name = "postSeq", sequenceName = "post_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeq")
    @NotNull
    @Column(name = "post_id")
    private Long postId;

    @NotNull
    @Column(name = "integration_post_id", unique = true)
    private String integrationPostId;

    @NotNull
    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    @NotNull
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @NotNull
    @Column(name = "integration_date")
    private LocalDateTime integrationDate;

    @NotNull
    @Column(name = "hidden")
    private boolean hidden;

    @NotNull
    @Column(name = "upvotes")
    private Integer upvotes;

    @NotNull
    @Column(name = "downvotes")
    private Integer downvotes;

}
