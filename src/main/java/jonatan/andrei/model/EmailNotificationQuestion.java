package jonatan.andrei.model;

import jonatan.andrei.domain.RecommendationType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="email_notification_question")
public class EmailNotificationQuestion {

    @Id
    @SequenceGenerator(name = "emailNotificationQuestionSeq", sequenceName = "email_notification_question_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emailNotificationQuestionSeq")
    @NotNull
    @Column(name = "email_notification_question_id")
    private Long emailNotificationQuestionId;

    @NotNull
    @Column(name = "email_notification_id")
    private Long emailNotificationId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "recommendation_type")
    @Enumerated(EnumType.STRING)
    private RecommendationType recommendationType;
}

