package jonatan.haas.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {

    @NotNull
    private String integrationTagId;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private boolean active;
}


