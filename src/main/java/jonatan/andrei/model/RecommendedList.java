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
@Table(name="recommended_list")
public class RecommendedList {

    @Id
    @SequenceGenerator(name = "recommendedListSeq", sequenceName = "recommended_list_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendedListSeq")
    @NotNull
    @Column(name = "recommended_list_id")
    private Long recommendedListId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "total_number_of_pages")
    private Integer totalNumberOfPages;

    @NotNull
    @Column(name = "total_number_of_questions")
    private Integer totalNumberOfQuestions;

    @NotNull
    @Column(name = "list_date")
    private LocalDateTime listDate;
}
