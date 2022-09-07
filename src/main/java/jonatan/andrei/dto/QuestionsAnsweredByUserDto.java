package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsAnsweredByUserDto {

    private String integrationUserId;

    private String integrationQuestionId;

    private Integer followers;

    private Long bestAnswerId;

    private Integer answers;

    private String tags;

    private LocalDateTime answerDate;

}
