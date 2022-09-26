package jonatan.andrei.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {

    @Id
    @SequenceGenerator(name = "notificationSeq", sequenceName = "notification_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationSeq")
    @NotNull
    @Column(name = "notification_id")
    private Long notificationId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "integration_user_id")
    private String integrationUserId;

    @NotNull
    @Column(name = "notification_date")
    private LocalDateTime sendDate;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "integration_question_id")
    private String integrationQuestionId;

    @NotNull
    @Column(name = "score")
    private BigDecimal score;

}

