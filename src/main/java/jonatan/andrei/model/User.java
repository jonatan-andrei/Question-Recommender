package jonatan.andrei.model;

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
    @Column(name = "integration_user_id", unique = true, length = 100)
    private String integrationUserId;

    @Column(name = "integration_anonymous_user_id", length = 100)
    private String integrationAnonymousUserId;

    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @NotNull
    @Column(name = "integration_date")
    private LocalDateTime integrationDate;

    @NotNull
    @Column(name = "anonymous")
    private boolean anonymous;

    @NotNull
    @Column(name = "active")
    private boolean active;

    @NotNull
    @Column(name = "email_notification_enable")
    private boolean emailNotificationEnable;

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

