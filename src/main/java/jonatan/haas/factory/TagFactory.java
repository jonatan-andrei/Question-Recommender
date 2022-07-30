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

    public static Tag overwrite (Tag existingTag, TagRequestDto tagRequestDto){
        existingTag.setIntegrationTagId(tagRequestDto.getIntegrationTagId());
        existingTag.setActive(tagRequestDto.isActive());
        existingTag.setName(tagRequestDto.getName());
        existingTag.setDescription(tagRequestDto.getDescription());
        return existingTag;
    }
}
