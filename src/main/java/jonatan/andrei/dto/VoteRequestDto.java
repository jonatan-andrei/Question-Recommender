package jonatan.andrei.dto;

import jonatan.andrei.domain.VoteType;
import jonatan.andrei.domain.VoteTypeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestDto {

    @NotBlank
    private String integrationPostId;

    @NotBlank
    private String integrationUserId;

    @NotNull
    private VoteTypeRequest voteType;

    private LocalDateTime voteDate;

}
