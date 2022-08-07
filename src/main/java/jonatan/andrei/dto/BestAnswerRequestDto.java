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
public class BestAnswerRequestDto {

    @NotBlank
    private String integrationQuestionId;

    @NotBlank
    private String integrationAnswerId;

    private boolean selected;
}
