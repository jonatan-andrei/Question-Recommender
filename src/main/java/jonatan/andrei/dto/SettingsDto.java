package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto {

    private Integer defaultLengthQuestionListPage;

    private Integer numberOfDaysQuestionIsRelevant;

    private Integer publicationDateRelevanceQuestionListPage;
}
