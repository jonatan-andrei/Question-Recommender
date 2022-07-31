package jonatan.andrei.model;

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
@Table(name="question_follower")
public class QuestionFollower {

    @Id
    @SequenceGenerator(name = "questionFollowerSeq", sequenceName = "question_follower_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionFollowerSeq")
    @NotNull
    @Column(name = "question_follower_id")
    private Long questionFollowerId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "date_followed")
    private LocalDateTime dateFollowed;
}
