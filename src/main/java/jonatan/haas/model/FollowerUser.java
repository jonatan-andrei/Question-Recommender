package jonatan.haas.model;

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
@Table(name="follower_user")
public class FollowerUser {

    @Id
    @SequenceGenerator(name = "followerUserSeq", sequenceName = "follower_user_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "followerUserSeq")
    @NotNull
    @Column(name = "follower_user_id")
    private Long followerUserId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "follower_id")
    private Long followerId;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

}
