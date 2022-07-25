package jonatan.haas.factory;

import jonatan.haas.dto.TagRequestDto;
import jonatan.haas.model.Tag;

public class TagFactory {

    public static Tag newTag(TagRequestDto tagRequestDto){
        return Tag.builder()
                .integrationTagId(tagRequestDto.getIntegrationTagId())
                .name(tagRequestDto.getName())
                .description(tagRequestDto.getDescription())
                .active(tagRequestDto.isActive())
                .questionCount(0)
                .build();
    }
}
