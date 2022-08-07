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
public class DuplicateQuestionRequestDto {

    @NotBlank
    private String integrationQuestionId;

    private String integrationDuplicateQuestionId;

}
