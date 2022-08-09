package jonatan.andrei.dto;

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
public class UserFollowerRequestDto {

    @NotBlank
    private String integrationUserId;

    @NotBlank
    private String integrationFollowerUserId;

    @NotNull
    private LocalDateTime startDate;

    private boolean followed;

}
