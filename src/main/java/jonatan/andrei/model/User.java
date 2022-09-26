package jonatan.andrei.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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
    @Column(name = "last_activity_date")
    private LocalDateTime lastActivityDate;

    @NotNull
    @Column(name = "number_questions_asked")
    private BigDecimal numberQuestionsAsked;

    @NotNull
    @Column(name = "number_questions_viewed")
    private BigDecimal numberQuestionsViewed;

    @NotNull
    @Column(name = "number_questions_answered")
    private BigDecimal numberQuestionsAnswered;

    @NotNull
    @Column(name = "number_questions_commented")
    private BigDecimal numberQuestionsCommented;

    @NotNull
    @Column(name = "number_questions_followed")
    private BigDecimal numberQuestionsFollowed;

    @NotNull
    @Column(name = "number_questions_upvoted")
    private BigDecimal numberQuestionsUpvoted;

    @NotNull
    @Column(name = "number_questions_downvoted")
    private BigDecimal numberQuestionsDownvoted;

    @NotNull
    @Column(name = "number_answers_upvoted")
    private BigDecimal numberAnswersUpvoted;

    @NotNull
    @Column(name = "number_answers_downvoted")
    private BigDecimal numberAnswersDownvoted;

    @NotNull
    @Column(name = "number_comments_upvoted")
    private BigDecimal numberCommentsUpvoted;

    @NotNull
    @Column(name = "number_comments_downvoted")
    private BigDecimal numberCommentsDownvoted;


}

