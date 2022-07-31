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
@Table(name="email_notification")
public class EmailNotification {

    @Id
    @SequenceGenerator(name = "emailNotificationSeq", sequenceName = "email_notification_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emailNotificationSeq")
    @NotNull
    @Column(name = "email_notification_id")
    private Long emailNotificationId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "notification_date")
    private LocalDateTime notificationDate;
}
