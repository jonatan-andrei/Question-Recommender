package jonatan.haas.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User {

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "user_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "integration_user_id", unique = true)
    private String integrationUserId;

    @Size(min = 1, max = 100)
    @Column(name = "original_session_id")
    private String originalSessionId;

    @NotNull
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @NotNull
    @Column(name = "integration_date")
    private LocalDateTime integrationDate;

    @NotNull
    @Column(name = "anonymous")
    private boolean anonymous;

    @Column(name = "anonymous_user_id")
    private Long anonymousUserId;

    @NotNull
    @Column(name = "active")
    private boolean active;

    @NotNull
    @Column(name = "email_notification_enable")
    private boolean emailNotificationEnable;

    @NotNull
    @Column(name = "email_notification_hour")
    private Integer emailNotificationHour;

    @NotNull
    @Column(name = "notification_enable")
    private boolean notificationEnable;

    @NotNull
    @Column(name = "recommendation_enable")
    private boolean recommendationEnable;

    @NotNull
    @Column(name = "last_activity_date")
    private LocalDateTime lastActivityDate;

}

