package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFollowerRequestDto {

    @NotBlank
    private String integrationQuestionId;

    @NotBlank
    private String integrationUserId;

    private boolean followed;
}
