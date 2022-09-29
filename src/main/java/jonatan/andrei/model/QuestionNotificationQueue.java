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
@Table(name = "question_notification_queue")
public class QuestionNotificationQueue {

    @Id
    @SequenceGenerator(name = "questionNotificationQueueSeq", sequenceName = "question_notification_queue_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionNotificationQueueSeq")
    @NotNull
    @Column(name = "question_notification_queue_id")
    private Long questionNotificationQueueId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

    @NotNull
    @Column(name = "integration_question_id")
    private String integrationQuestionId;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    @Column(name = "date_was_ignored")
    private LocalDateTime dateWasIgnored;

}
