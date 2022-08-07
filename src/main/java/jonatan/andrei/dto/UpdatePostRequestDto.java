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

    private PostType newPostType;

    private String contentOrDescription;

    private String url;

    List<String> integrationCategoriesIds;

    List<String> integrationTagsIds;

}
