package jonatan.andrei.dto;

import jonatan.andrei.domain.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequestDto {

    @NotBlank
    private String integrationPostId;

    private String integrationParentPostId;

    @NotNull
    private PostType postType;

    @NotBlank
    private LocalDateTime publicationDate;

    private String title;

    private String contentOrDescription;

    private String url;

    List<String> integrationCategoriesIds;

    List<String> tags;

    private String integrationUserId;

    private String integrationAnonymousUserId;

}
