package jonatan.andrei.model;

import jonatan.andrei.domain.VoteType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="vote")
public class Vote {

    @Id
    @SequenceGenerator(name = "voteSeq", sequenceName = "vote_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voteSeq")
    @NotNull
    @Column(name = "vote_id")
    private Long voteId;

    @NotNull
    @Column(name = "post_id")
    private Long postId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "vote_type")
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @NotNull
    @Column(name = "vote_date")
    private LocalDateTime voteDate;
}
