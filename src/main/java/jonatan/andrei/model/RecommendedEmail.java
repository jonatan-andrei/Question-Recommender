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
@Table(name="recommended_email")
public class RecommendedEmail {

    @Id
    @SequenceGenerator(name = "recommendedEmailSeq", sequenceName = "recommended_email_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendedEmailSeq")
    @NotNull
    @Column(name = "recommended_email_id")
    private Long recommendedEmailId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "send_date")
    private LocalDateTime sendDate;
}
