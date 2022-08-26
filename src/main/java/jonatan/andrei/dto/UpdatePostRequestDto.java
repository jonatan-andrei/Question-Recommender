package jonatan.andrei.dto;

import jonatan.andrei.domain.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequestDto {

    @NotBlank
    private String integrationPostId;

    @NotBlank
    private PostType postType;

    private String title;

    private String contentOrDescription;

    private String url;

    private List<String> integrationCategoriesIds;

    private List<String> tags;

}
