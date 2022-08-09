package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewsRequestDto {

    @NotBlank
    private String integrationQuestionId;

    private List<String> integrationUsersId;

    @NotNull
    private Integer totalViews;

}
