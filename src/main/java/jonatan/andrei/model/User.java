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

    @NotNull
    @Column(name = "number_questions_asked")
    private Integer numberQuestionsAsked;

    @NotNull
    @Column(name = "number_questions_viewed")
    private Integer numberQuestionsViewed;

    @NotNull
    @Column(name = "number_questions_answered")
    private Integer numberQuestionsAnswered;

    @NotNull
    @Column(name = "number_questions_commented")
    private Integer numberQuestionsCommented;

    @NotNull
    @Column(name = "number_questions_followed")
    private Integer numberQuestionsFollowed;

    @NotNull
    @Column(name = "number_questions_upvoted")
    private Integer numberQuestionsUpvoted;

    @NotNull
    @Column(name = "number_questions_downvoted")
    private Integer numberQuestionsDownvoted;

    @NotNull
    @Column(name = "number_answers_upvoted")
    private Integer numberAnswersUpvoted;

    @NotNull
    @Column(name = "number_answers_downvoted")
    private Integer numberAnswersDownvoted;

    @NotNull
    @Column(name = "number_comments_upvoted")
    private Integer numberCommentsUpvoted;

    @NotNull
    @Column(name = "number_comments_downvoted")
    private Integer numberCommentsDownvoted;


}

